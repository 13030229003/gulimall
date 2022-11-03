package com.han.gulimall.seckill.scheduled;

import com.han.gulimall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * 秒杀商品定时上架
 *  每天晚上3点，上架最近三天需要三天秒杀的商品
 *  当天00:00:00 - 23:59:59
 *  明天00:00:00 - 23:59:59
 *  后天00:00:00 - 23:59:59
 */

/**
 * @author xsh
 */
@Slf4j
@EnableScheduling
@Service
public class SeckillScheduled {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedissonClient redissonClient;

    //秒杀商品上架功能的锁
    private final String upload_lock = "seckill:upload:lock";


    /**
     *  秒 分 时 日 月 周
     *
     *  上架   秒杀商品  的定时任务
     *
     */
//    @Scheduled(cron = "30 * * * * ?")
    @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点，查询数据库，获取未来三天需要秒杀的商品，放到redis中
    public void uploadSeckillSkuLatest3Days() {

        log.info("扫描需要上架秒杀的商品");


        /**
         * 当开启多个服务节点时，都执行定时任务，需要使用分布式锁来锁住这个执行，只有一个服务节点可以执行
         *
         *      当一个服务上架了秒杀商品，释放了锁，其他服务获取到这个锁，发现秒杀商品已将上架了，就不用继续上架商品了
         *
         */
        RLock lock = redissonClient.getLock(upload_lock);
        try {
            /**
             * 加锁报错问题：如果redis设置了密码，配置redisson时需要给RedissonClient设置一个密码
             */
            lock.lock(10l, TimeUnit.SECONDS);
            seckillService.uploadSeckillSkuLatest3Days();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
