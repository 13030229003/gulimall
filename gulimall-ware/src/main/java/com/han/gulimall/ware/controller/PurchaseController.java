package com.han.gulimall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.han.gulimall.ware.vo.MergeVo;
import com.han.gulimall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.han.gulimall.ware.entity.PurchaseEntity;
import com.han.gulimall.ware.service.PurchaseService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.common.utils.R;



/**
 * 采购信息
 *
 * @author xushuhan
 * @email 10086@gmail.com
 * @date 2022-10-11 12:08:18
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 完成采购单
     * @param doneVo
     * @return
     */
    @PostMapping(value = "/done")
    public R finish(@RequestBody PurchaseDoneVo doneVo) {

        purchaseService.done(doneVo);

        return R.ok();
    }

    /**
     * 员工领取采购单
     * @param ids
     * @return
     */
    @PostMapping(value = "/received")
    public R received(@RequestBody List<Long> ids) {

        purchaseService.received(ids);

        return R.ok();
    }

    /**
     * 合并整单
     * @param mergeVo
     * @return
     */
    ///ware/purchase/merge
    @PostMapping(value = "/merge")
    public R merge(@RequestBody MergeVo mergeVo) {

        purchaseService.mergePurchase(mergeVo);

        return R.ok();
    }

    @GetMapping(value = "/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPageUnreceive(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase){
		purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
