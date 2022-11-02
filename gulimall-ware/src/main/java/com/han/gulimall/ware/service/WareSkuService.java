package com.han.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.gulimall.common.to.OrderTo;
import com.han.gulimall.common.to.mq.StockLockedTo;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.ware.entity.WareSkuEntity;
import com.han.gulimall.ware.vo.SkuHasStockVo;
import com.han.gulimall.ware.vo.WareSkuLockVo;

import java.util.List;
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

    /**
     * 添加库存
     * @param skuId
     * @param wareId
     * @param skuNum
     */
    void addStock(Long skuId, Long wareId, Integer skuNum);

    /**
     * 判断是否有库存
     * @param skuIds
     * @return
     */
    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    /**
     * 锁定库存
     * @param vo
     * @return
     */
    boolean orderLockStock(WareSkuLockVo vo);


    /**
     * 到了库存解锁的时间，都没有处理，解锁库存
     * @param to
     */
    void unlockStock(StockLockedTo to);

    /**
     * 订单被取消，立刻解锁库存
     * @param to
     */
    void unlockStock(OrderTo to);

}

