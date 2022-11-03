package com.han.gulimall.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


/**
 * @author xsh
 */
@Configuration
public class MyRedissonConfig {

    /**
     * 所有对Redisson的使用都是通过RedissonClient
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod="shutdown")
    public RedissonClient redissonClient() throws IOException {
        //1、创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.19.130:6379");

        /**
         *      秒杀服务踩坑：定时任务 redis枷锁的问题
         *
         *  RLock lock = redissonClient.getLock("seckill:upload:lock");
         *  lock.lock(10l, TimeUnit.SECONDS);
         *
         *          使用分布式锁，如果redis设置了密码，一定要加入密码，不然会报错
         */

        config.useSingleServer().setPassword("123456");
        //2、根据Config创建出RedissonClient实例
        //Redis url should start with redis:// or rediss://
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

}
