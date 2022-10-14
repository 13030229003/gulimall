package com.han.gulimall.order.dao;

import com.han.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author xushuhan
 * @email 10086@gmail.com
 * @date 2022-10-11 12:00:51
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
