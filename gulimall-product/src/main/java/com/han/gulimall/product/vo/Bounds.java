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
public class Bounds implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal buyBounds;
    private BigDecimal growBounds;

}
