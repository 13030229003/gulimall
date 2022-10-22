/**
  * Copyright 2021 bejson.com
  */
package com.han.gulimall.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yaoxinjia
 */
@Data
public class MemberPrice implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private BigDecimal price;


}
