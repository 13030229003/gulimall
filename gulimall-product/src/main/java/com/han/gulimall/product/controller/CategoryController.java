package com.han.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.han.gulimall.product.entity.CategoryEntity;
import com.han.gulimall.product.service.CategoryService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.common.utils.R;



/**
 * 商品三级分类
 *
 * @author xushuhan
 * @email sunlightcs@gmail.com
 * @date 2022-10-12 20:28:36
 */
@RestController
@RequestMapping("/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类以及子分类，以树形结构组装起来
     * @param params
     * @return
     */
    @RequestMapping("/list/tree")
    public R list(@RequestParam Map<String, Object> params){
        List<CategoryEntity> entities = categoryService.listWithTree();

        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }


    /**
     * 修改分类：拖拽修改分类
     * @param category
     * @return
     */
    @RequestMapping("/update/sort")
    public R update(@RequestBody CategoryEntity[] category){

//        System.out.println(Arrays.asList(category));

        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }

//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    public R update(@RequestBody CategoryEntity category){
//
////        System.out.println(category);
//
//		categoryService.updateById(category);
//
//        return R.ok();
//    }
    /**
     * 修改
     * @param category
     * @return
     *  更新分类，也将品牌和分类关联表中的名称也改变
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
        categoryService.updateCasecade(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){

//        System.out.println(Arrays.asList(catIds));

//		categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.removeMenuByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
