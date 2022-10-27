package com.han.gulimall.search.vo;

import lombok.Data;

/**
 * @author yaoxinjia
 */
@Data
public class BrandVo {
    private Long brandId;
    private String brandName;

    /**
     * 品牌名实体类的名字是name，和BrandVo的brandName对不上
     */
    private String name;
}
