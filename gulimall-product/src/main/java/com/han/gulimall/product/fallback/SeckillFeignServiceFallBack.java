package com.han.gulimall.product.fallback;

import com.han.gulimall.common.exception.BizCodeEnum;
import com.han.gulimall.common.utils.R;
import com.han.gulimall.product.feign.SeckillFeignService;

import org.springframework.stereotype.Component;

/**
 * @author xsh
 */
@Component
public class SeckillFeignServiceFallBack implements SeckillFeignService {
    @Override
    public R getSkuSeckilInfo(Long skuId) {
        return R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(),BizCodeEnum.TO_MANY_REQUEST.getMsg());
    }
}
