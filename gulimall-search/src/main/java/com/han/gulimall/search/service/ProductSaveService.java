package com.han.gulimall.search.service;


import com.han.gulimall.common.to.es.SkuEsModel;

import java.util.List;

/**
 * @author yaoxinjia
 */
public interface ProductSaveService {
//    boolean productStatusUp(List<SkuEsModel> skuEsModels);
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws Exception;
}
