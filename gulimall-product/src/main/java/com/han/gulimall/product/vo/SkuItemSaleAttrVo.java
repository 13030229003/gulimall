package com.han.gulimall.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoxinjia
 */
@Data
public class SkuItemSaleAttrVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}
