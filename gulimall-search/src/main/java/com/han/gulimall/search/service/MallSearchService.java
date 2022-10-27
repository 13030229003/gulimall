package com.han.gulimall.search.service;


import com.han.gulimall.search.vo.SearchParam;
import com.han.gulimall.search.vo.SearchResult;

/**
 * @author yaoxinjia
 */
public interface MallSearchService {
    SearchResult search(SearchParam param);
}
