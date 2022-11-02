package com.han.gulimall.order.vo;

import com.han.gulimall.order.entity.OrderEntity;
import lombok.Data;

/**
 * @author xsh
 * @email 10010@qq.com
 */
@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;

    /** 错误状态码 **/
    private Integer code;


}
