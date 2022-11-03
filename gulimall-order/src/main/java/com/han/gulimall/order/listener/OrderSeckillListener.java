package com.han.gulimall.order.listener;

import com.rabbitmq.client.Channel;
import com.han.gulimall.common.to.mq.SeckillOrderTo;
import com.han.gulimall.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xsh
 * @email 10010@qq.com
 *
 *  监听 商品秒杀队列
 *
 *   待解决的问题： 用户秒杀抢到商品后，如果不付款，订单超时后，秒杀商品的数据不会加回。
 *          解决办法：方案一：  抢商品时不判断商品秒杀商品的数量是否足够，只有在付款是再判断商品秒杀库存是否还有。
 *                   方案二：  生成秒杀单后，给MQ发消息，延时队列，到了时间还没付款，就将订单取消，把秒杀商品库存加回。
 */
@Slf4j
@Component
@RabbitListener(queues = "order.seckill.order.queue")
public class OrderSeckillListener {

    @Autowired
    private OrderService orderService;


    @RabbitHandler
    public void listener(SeckillOrderTo orderTo, Channel channel, Message message) throws IOException {

        log.info("准备创建秒杀单的详细信息...");

        try {
            orderService.createSeckillOrder(orderTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }

    }

}
