package com.han.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.han.gulimall.product.service.CategoryBrandRelationService;
import com.han.gulimall.product.utils.RedisKeyUtils;
import com.han.gulimall.product.utils.RedisUtil;
import com.han.gulimall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.common.utils.Query;

import com.han.gulimall.product.dao.CategoryDao;
import com.han.gulimall.product.entity.CategoryEntity;
import com.han.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    @Autowired
    private RedisUtil redisUtil;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 1 查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // 2 组装成父子的树形结构
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid().longValue() == 0
        ).map((menu) -> {
            menu.setChildren(getChildrens(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO 1 检查当前删除的菜单，是否被别的地方引用

        // 逻辑删除
        baseMapper.deleteBatchIds(asList);

    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        //逆序转换
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联的数据
     *
     * @param category
     */
    // 批量清除，该分类下的缓存
    @CacheEvict(value = "category", allEntries = true)  // 失效模式
//    @CachePut // 双写模式,方法需要有返回值
//    @CacheEvict(value = {"category"},key = "'getLevel1Categorys'") // 更新操作，就删除缓存
    @Transactional
    @Override
    public void updateCasecade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }



    /**
     *     访问首页，调用这个方法
     *
     *      @Cacheable(value = {"category"})
     *    每一个需要缓存的数据我们都要指定放到哪个名字的缓存。【缓存的分区】
     *
     *    当前方法的结果需要缓存，如果缓存中有，方法不调用，如果缓存中没有，会调用方法，最后将方法的结果放入缓存
                 @Cacheable(value = {"category"},key = "#root.method.name",sync = true) sync:开启同步
     */

    @Cacheable(value = {"category"},key = "#root.method.name",sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        System.out.println("消耗时间，" + (System.currentTimeMillis() - l));
        return categoryEntities;
    }


    //225,25,2，需要进行逆序转换
    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        // 1 收集当前节点id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

    /**
     * 递归查找所有菜单的子菜单
     *
     * @param root
     * @param all
     * @return
     */
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().longValue() == root.getCatId().longValue();  // 注意此处应该用longValue()来比较，否则会出先bug，因为parentCid和catId是long类型
        }).map(categoryEntity -> {
            // 1 找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            // 2 菜单的排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }

    //TODO 产生堆外内存溢出：outofoirectMemoryError
    //1）、springboot2.e以后默认使用Lettuce作为操作redis的客户端。它使用netty进行网络通信。
    //2）、Lettuce的bug导致netty堆外内存溢出-Xmx300m；netty如果没有指定堆外内存，默认使用-Xmx300m
    //可以通过-Dio.netty.maxDirectMemory进行设置
    //解决方案：不能使用-Dio.netty.maxDirectNemory只去调大堆外内存。
    //1）、升级Lettuce客户端。2）、切换使用jedis
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {


        // 从redis中获取category
        String catalogJSON = (String) redisUtil.get(RedisKeyUtils.MAP_KEY_CATEGORY_JSON);
        // redis中没有数据，查询数据库
        if (StringUtils.isEmpty(catalogJSON)) {
            System.out.println("缓存不命中，要查询数据库....");

            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonForDbWithRedisLook();
            return catalogJsonFromDb;

        }

        System.out.println("缓存命中....直接返回....");
        // redis中有数据，将数据转为Map，然后返回给前端
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {});
        return result;

    }

    @Autowired
    private RedisTemplate redisTemplate;

    public Map<String, List<Catelog2Vo>> getCatalogJsonForDbWithRedisLook() {


        String uuid = UUID.randomUUID().toString();
        // 分布式锁，
//        Boolean look = redisUtil.setNX(RedisKeyUtils.BOOLEAN_KEY_CATEGORY_REDIS_NX, uuid,300,TimeUnit.SECONDS);

        Boolean lock = redisTemplate.opsForValue().setIfAbsent(RedisKeyUtils.BOOLEAN_KEY_CATEGORY_REDIS_NX, uuid, 30, TimeUnit.SECONDS);

        // 获取锁
        if (lock) {
            System.out.println("获取锁成功:" + lock +"  uuid:" + uuid);
            Map<String, List<Catelog2Vo>> dataFromDb;
            try {
                // 查数据库，返回给前端
                dataFromDb = getCatalogJsonForDb();
            }finally {

                // 使用lua脚本，原子操作，进行获取uuid和删除uuid
                String script  = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

                redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList(RedisKeyUtils.BOOLEAN_KEY_CATEGORY_REDIS_NX), uuid);

//                Integer look1 =
//                        redisTemplate.execute(new DefaultRedisScript<Integer>(script, Integer.class),
//                                Arrays.asList(RedisKeyUtils.BOOLEAN_KEY_CATEGORY_REDIS_NX), uuid);
            }
            return dataFromDb;
        } else {
            // 加载失败.... 重试。
            // 休眠100ms重试
            try {
                Thread.sleep(200);
                System.out.println("锁失败重试.....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getCatalogJson();
        }
    }
//    public Map<String, List<Catelog2Vo>> getCatalogJsonForDbWithRedisLook() {
//        // 分布式锁，
//        Boolean look = redisUtil.setNX(RedisKeyUtils.BOOLEAN_KEY_CATEGORY_REDIS_NX, RedisKeyUtils.BOOLEAN_KEY_CATEGORY_REDIS_NX);
//        // 获取锁
//        if (look) {
//            // 查数据库，返回给前端
//            Map<String, List<Catelog2Vo>> dataFromDb = getCatalogJsonForDb();
//            redisUtil.remove(RedisKeyUtils.BOOLEAN_KEY_CATEGORY_REDIS_NX); // 数据出入到redis中后，删除分布式锁
//            return dataFromDb;
//        } else {
//            // 加载失败.... 重试。
//            // 休眠100ms重试
//            return getCatalogJson();
//        }
//    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonForDb() {

        // 从redis中获取category
        String catalogJSON = (String) redisUtil.get(RedisKeyUtils.MAP_KEY_CATEGORY_JSON);

        // 如果不为空，返回前端
        if (!StringUtils.isEmpty(catalogJSON)) {
            // redis中有数据，将数据转为Map，然后返回给前端
            Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
            return result;
        }
        /**
         * redis 中没有数据，查数据库，出入redis。返回数据
         */
        List<CategoryEntity> selectList = baseMapper.selectList(null);
//        System.out.println(selectList);
        // 查询所有一级分类
        List<CategoryEntity> level1Category = getParent_cid(selectList, 0L);

        // 2 封装数据
        Map<String, List<Catelog2Vo>> parent_cid = level1Category.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                    // 1 每一个的一级分类，查到这个一级分类的二级分类
                    List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
                    // 2 分装上面的结果
                    List<Catelog2Vo> catelog2Vos = null;

                    if (categoryEntities != null) {

                        catelog2Vos = categoryEntities.stream().map(l2 -> {
                            Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                            // 1 找当前二级分类的三级分类封装成vo
                            List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());
                            if (level3Catelog != null) {
                                List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
                                    // 2 分装成指定格式
                                    Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                                    return catelog3Vo;
                                }).collect(Collectors.toList());
                                catelog2Vo.setCatalog3List(collect);
                            }
                            return catelog2Vo;
                        }).collect(Collectors.toList());
                    }
                    return catelog2Vos;

                }

        ));

        /**
         * 查数据库和存入缓存，需要在同一个锁中完成
         */
        // 将Map转为String，然后存到redis中
        String s = JSON.toJSONString(parent_cid);
        redisUtil.set(RedisKeyUtils.MAP_KEY_CATEGORY_JSON, s, 1L, TimeUnit.DAYS);// 失效时间为一天
        System.out.println("将数据存入redis中....");
        return parent_cid;

    }

//    @Cacheable(value = "category",key = "#root.methodName")

    public Map<String, List<Catelog2Vo>> getCatalogJsonForDbWithSyncLook() {

        synchronized (this) {

            List<CategoryEntity> selectList = baseMapper.selectList(null);
//        System.out.println(selectList);
            // 查询所有一级分类
            List<CategoryEntity> level1Category = getParent_cid(selectList, 0L);

            // 2 封装数据
            Map<String, List<Catelog2Vo>> parent_cid = level1Category.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                        // 1 每一个的一级分类，查到这个一级分类的二级分类
                        List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
                        // 2 分装上面的结果
                        List<Catelog2Vo> catelog2Vos = null;

                        if (categoryEntities != null) {

                            catelog2Vos = categoryEntities.stream().map(l2 -> {
                                Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                                // 1 找当前二级分类的三级分类封装成vo
                                List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());
                                if (level3Catelog != null) {
                                    List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
                                        // 2 分装成指定格式
                                        Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                                        return catelog3Vo;
                                    }).collect(Collectors.toList());
                                    catelog2Vo.setCatalog3List(collect);
                                }
                                return catelog2Vo;
                            }).collect(Collectors.toList());
                        }
                        return catelog2Vos;

                    }

            ));

            /**
             * 查数据库和存入缓存，需要在同一个锁中完成
             */

            // 将Map转为String，然后存到redis中
            String s = JSON.toJSONString(parent_cid);
            redisUtil.set(RedisKeyUtils.MAP_KEY_CATEGORY_JSON, s, 1L, TimeUnit.DAYS);
            return parent_cid;


        }


    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parent_cid) {
        List<CategoryEntity> collect = selectList.stream().filter(item -> {
            return item.getParentCid() == parent_cid;
        }).collect(Collectors.toList());
        return collect;
        // return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
    }

}
