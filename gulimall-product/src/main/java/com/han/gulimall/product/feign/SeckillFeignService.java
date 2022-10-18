//package com.han.gulimall.product.feign;
//
//
//import com.han.gulimall.common.utils.R;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
///**
// * @author yaoxinjia
// */
//@FeignClient(value = "gulimall-seckill",fallback = SeckillFeignServiceFallBack.class)
//public interface SeckillFeignService {
//
//    /**
//     * 根据skuId查询商品是否参加秒杀活动
//     * @param skuId
//     * @return
//     */
//    @GetMapping(value = "/sku/seckill/{skuId}")
//    R getSkuSeckilInfo(@PathVariable("skuId") Long skuId);
//
//}
