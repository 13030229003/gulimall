package com.han.gulimall.member.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.han.gulimall.coupon.config
 * @Author XSH
 * @Date 2022-10-12 15:31
 * @Version 1.0.0
 * @Description： 绑定nacos配置中心的数据。
 **/

@Component
@Data
@ConfigurationProperties(prefix = "user")
public class UserProperties {

    private String name;

    private String password;


}
