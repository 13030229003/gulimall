package com.han.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author xushuhan
 * @email sunlightcs@gmail.com
 * @date 2022-10-12 20:28:36
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

