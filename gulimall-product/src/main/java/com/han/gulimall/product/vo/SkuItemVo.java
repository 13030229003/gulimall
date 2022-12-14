package com.han.gulimall.product.vo;

import com.han.gulimall.product.entity.SkuImagesEntity;
import com.han.gulimall.product.entity.SkuInfoEntity;

import com.han.gulimall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoxinjia
 */
@Data
public class SkuItemVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //1、sku基本信息获取 pms_sku_info
    SkuInfoEntity info;

    boolean hasStock = true;//库存
    //2、sku图片信息    pms_sku_images
    List<SkuImagesEntity> images;
    //3、获取spu的销售属性组合
    List<SkuItemSaleAttrVo> saleAttr;
    //4、获取spu的介绍
    SpuInfoDescEntity desp;
    //5、获取spu的规格参数信息
    List<SpuItemAttrGroupVo> groupAttrs;

    //6、秒杀商品的优惠信息
    private SeckillSkuVo seckillSkuVo;


}
