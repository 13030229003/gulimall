package com.han.gulimall.product.web;

import com.han.gulimall.product.entity.CategoryEntity;
import com.han.gulimall.product.service.CategoryService;
import com.han.gulimall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author yaoxinjia
 */
@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;
    // 1 查询所有的1级分类



    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", categoryEntities);

//        System.out.println(categoryEntities.toString());


        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;

    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }



}
