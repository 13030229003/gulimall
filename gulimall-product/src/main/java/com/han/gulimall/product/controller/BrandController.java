package com.han.gulimall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.han.gulimall.common.valid.AddGroup;
import com.han.gulimall.common.valid.UpdateGroup;
import com.han.gulimall.common.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.han.gulimall.product.entity.BrandEntity;
import com.han.gulimall.product.service.BrandService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.common.utils.R;



/**
 * 品牌
 *
 * @author xushuhan
 * @email sunlightcs@gmail.com
 * @date 2022-10-12 20:28:36
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand){

        /** public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand, BindingResult result)
         * 统一异常处理，
         *         HashMap<String, String> map = new HashMap<>();
         *
         *         //1、获取校验的错误结果
         *         if (result.hasErrors()) {
         *             result.getFieldErrors().forEach((item) ->{
         *
         *                 // 获取错误提示
         *                 String message = item.getDefaultMessage();
         *
         *                 // 获取错误的属性的名字（类字段）
         *                 String field = item.getField();
         *
         *                 map.put(field,message);
         *
         *             });
         *             return R.error(400,"提交的数据不合法").put("data",map);
         *         } else {
         *             brandService.save(brand);
         *             return R.ok();
         *         }
         */

        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }


    /**
     * 修改状态
     * @param brand
     * @return
     */
    @RequestMapping("/update/status")
    public R updateStatus(@Validated({UpdateStatusGroup.class}) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
