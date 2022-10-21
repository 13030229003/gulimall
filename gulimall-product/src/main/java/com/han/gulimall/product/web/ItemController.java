//package com.han.gulimall.product.web;
//
//import com.han.gulimall.product.service.SkuInfoService;
//import com.han.gulimall.product.vo.SkuItemVo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.concurrent.ExecutionException;
//
///**
// * @author yaoxinjia
// */
//@Controller
//public class ItemController {
//
//    @Autowired
//    private SkuInfoService skuInfoService;
//
//    @RequestMapping("/{skuId}.html")
//    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException, InterruptedException {
//
//        SkuItemVo vo = skuInfoService.item(skuId);
//
//        model.addAttribute("item", vo);
//        return "item";
//    }
//}
