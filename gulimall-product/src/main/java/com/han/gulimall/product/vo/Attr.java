/**
  * Copyright 2021 bejson.com
  */
package com.han.gulimall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 * @author yaoxinjia
 */
@ToString
@Data
public class Attr implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long attrId;
    private String attrName;
    private String attrValue;

}
