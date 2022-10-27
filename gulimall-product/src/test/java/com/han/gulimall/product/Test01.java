package com.han.gulimall.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @PACKAGE_NAME: com.han.test
 * @Author XSH
 * @Date 2022-10-24 09:51
 * @Version 1.0.0
 * @Description：
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test01 {

    @Resource
    RedissonClient redissonClient;

    @Test
    public void test(){

        System.out.println(redissonClient);

    }

}
