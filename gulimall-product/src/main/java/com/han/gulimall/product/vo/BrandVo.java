package com.han.gulimall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yaoxinjia
 */
@Data
public class BrandVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long brandId;
    private String brandName;
}
