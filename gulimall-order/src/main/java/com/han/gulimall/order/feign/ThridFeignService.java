//package com.han.gulimall.order.feign;
//
//import com.alipay.api.AlipayApiException;
//import com.han.gulimall.order.vo.PayVo;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//
///**
// * @author yaoxinjia
// * @email 894548575@qq.com
// */
//@FeignClient("gulimall-third-party")
//public interface ThridFeignService {
//
//    @GetMapping(value = "/pay",consumes = "application/json")
//    String pay(@RequestBody PayVo vo) throws AlipayApiException;
//
//}
