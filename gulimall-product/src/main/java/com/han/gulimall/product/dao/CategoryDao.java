package com.han.gulimall.product.dao;

import com.han.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author xushuhan
 * @email sunlightcs@gmail.com
 * @date 2022-10-12 20:28:36
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
