package com.han.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import com.han.gulimall.coupon.config.UserProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.han.gulimall.coupon.entity.CouponEntity;
import com.han.gulimall.coupon.service.CouponService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.common.utils.R;



/**
 * 优惠券信息
 *
 * @author xushuhan
 * @email 10086@gmail.com
 * @date 2022-10-11 11:36:46
 */
//@Slf4j
@RestController
@RequestMapping("/coupon/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;


    /**
     * 测试feign
     */
    @RequestMapping("/openFeignTest")
    public R testOpenFeign(){

        return R.ok().put("coupon", "hello,openFeign");
    }

    @Autowired
    private UserProperties userProperties;

    /**
     * 测试NaCos配置中心
     */
    @RequestMapping("/naCosConfiguration")
    public R testNaCosConfiguration(){

        return R.ok().put("user",userProperties);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
