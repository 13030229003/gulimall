package com.han.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.han.gulimall.product.vo.AttrRespVo;
import com.han.gulimall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.han.gulimall.product.entity.AttrEntity;
import com.han.gulimall.product.service.AttrService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.common.utils.R;



/**
 * 商品属性
 *
 * @author xushuhan
 * @email sunlightcs@gmail.com
 * @date 2022-10-12 20:28:36
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;


    /**
     * 获取规格参数
     * @param params
     * @param catelogId
     * @param type
     * @return
     */
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("attrType")String type) {
        PageUtils page = attrService.queryBaseAttrPage(params,catelogId,type);
        return R.ok().put("page",page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{attrId}")
//    public R info(@PathVariable("attrId") Long attrId){
//		AttrEntity attr = attrService.getById(attrId);
//
//        return R.ok().put("attr", attr);
//    }
    /**
     * 信息，获取attr信息，并将attr
     */
    @RequestMapping("/info/{attrId}")
    // @RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
//		AttrEntity attr = attrService.getById(attrId);
        AttrRespVo respVo = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", respVo);
    }

    /**
     * 保存
     */
//    @RequestMapping("/save")
//    public R save(@RequestBody AttrEntity attr){
//		attrService.save(attr);
//
//        return R.ok();
//    }

    /**
     * 保存
     *   规格参数和销售属性公用一个save方法，只是显示的内容有所不同。
     *              规格参数   有分类和分组。
     *              销售属性   只有分类，没有分组。
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){
        attrService.saveAttr(attr);

        return R.ok();
    }

//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    public R update(@RequestBody AttrEntity attr){
//		attrService.updateById(attr);
//
//        return R.ok();
//    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:attr:update")
        public R update(@RequestBody AttrVo attr){

//        System.out.println(attr);
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
