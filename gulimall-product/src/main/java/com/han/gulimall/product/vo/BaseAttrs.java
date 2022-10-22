/**
  * Copyright 2021 bejson.com
  */
package com.han.gulimall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yaoxinjia
 */
@Data
public class BaseAttrs implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long attrId;
    private String attrValues;
    private int showDesc;
}
