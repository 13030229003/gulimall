package com.han.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author xushuhan
 * @email 10086@gmail.com
 * @date 2022-10-11 12:08:18
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

