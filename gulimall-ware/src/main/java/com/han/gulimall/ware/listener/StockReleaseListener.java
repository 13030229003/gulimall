package com.han.gulimall.ware.listener;

import com.han.gulimall.common.to.OrderTo;
import com.han.gulimall.common.to.mq.StockLockedTo;
import com.han.gulimall.ware.service.WareSkuService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @PACKAGE_NAME: com.han.gulimall.ware.listener
 * @Author XSH
 * @Date 2022-11-01 17:19
 * @Version 1.0.0
 * @Description：
 **/

@RabbitListener(queues = "stock.release.stock.queue")
@Service
public class StockReleaseListener {

    @Autowired
    WareSkuService wareSkuService;

    /**
     * 到了库存解锁的时间，都没有处理，解锁库存
     * @param
     */
    @RabbitHandler
    public void handleStockLockedRelease(StockLockedTo to, Message message, Channel channel) throws IOException {

        System.out.println("收到解锁库存的消息.......");
        try {
            wareSkuService.unlockStock(to);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            /**
             * 接收到异常，将消息重新放到队列中，给别人消费
             */
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);

        }



    }

    /**
     * 订单被取消，立刻解锁库存
     * @param
     */
    @RabbitHandler
    public void handleOrderCloseRelease(OrderTo to, Message message, Channel channel) throws IOException {

        System.out.println("订单关闭，直接解锁库存.......");
        try {
            wareSkuService.unlockStock(to);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            /**
             * 接收到异常，将消息重新放到队列中，给别人消费
             */
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);

        }



    }

}
