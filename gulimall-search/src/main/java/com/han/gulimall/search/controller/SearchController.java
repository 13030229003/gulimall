package com.han.gulimall.search.controller;

import com.han.gulimall.search.service.MallSearchService;
import com.han.gulimall.search.vo.SearchParam;
import com.han.gulimall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @PACKAGE_NAME: com.han.gulimall.search.controller
 * @Author XSH
 * @Date 2022-10-25 10:20
 * @Version 1.0.0
 * @Description：
 **/
@Controller
public class SearchController {
    @Autowired
    MallSearchService mallSearchService;
    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {
        param.set_queryString(request.getQueryString());
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }
}
