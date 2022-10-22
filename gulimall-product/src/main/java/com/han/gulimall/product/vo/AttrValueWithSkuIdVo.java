package com.han.gulimall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yaoxinjia
 */
@Data
public class AttrValueWithSkuIdVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String attrValue;
    private String skuIds;
}
