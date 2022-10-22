/**
  * Copyright 2021 bejson.com
  */
package com.han.gulimall.product.vo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yaoxinjia
 */
@Data
public class SpuSaveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;
    private BigDecimal weight;
    private int publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;

}
