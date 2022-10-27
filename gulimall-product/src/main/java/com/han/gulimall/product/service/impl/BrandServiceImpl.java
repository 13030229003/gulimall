package com.han.gulimall.product.service.impl;

import com.han.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.common.utils.Query;

import com.han.gulimall.product.dao.BrandDao;
import com.han.gulimall.product.entity.BrandEntity;
import com.han.gulimall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {


    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 1 获取key
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("brand_id", key).or().like("name", key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    // 两个修改操作，开启事务
    @Transactional
    @Override
    /**
     * 修改品牌名称的话， 需要将品牌和分类的关联表的冗余字段也进行修改。（CategoryBrandRelation）
     */
    public void updateDetail(BrandEntity brand) {
        this.updateById(brand); // 更新品牌brand
        // 如果品牌更新中，有名字的更新，那么品牌和分类关联表的名称也要进行修改。
        if (!StringUtils.isEmpty(brand.getName())) {
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName()); // 更新品牌和分类关联表的名称

            // TODO 更新其他关联
        }
    }

    @Override
    public List<BrandEntity> getBrandsByIds(List<Long> brandIds) {

        return  baseMapper.selectList(new QueryWrapper<BrandEntity>().in("brand_id",brandIds));
    }

}
