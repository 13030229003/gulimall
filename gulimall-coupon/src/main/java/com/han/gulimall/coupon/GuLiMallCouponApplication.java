package com.han.gulimall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@MapperScan("com.han.gulimall.coupon.dao")
/*
开启服务注册和发现
 */
@EnableDiscoveryClient
public class GuLiMallCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuLiMallCouponApplication.class, args);
	}
}
