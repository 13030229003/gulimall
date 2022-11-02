package com.han.gulimall.order.config;

import  feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author xsh
 * @email 10010@qq.com
 *
 * 远程调用发起就拦截，执行这个类的方法。
 */
@Configuration
public class GuliFeignConfig {

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            /**
             *
             * @param template 给调用添加cookie，不然会导致远程调用的controller拿不到cookie，
             *                  因为远程调用不会有cookie，cookie是浏览器携带过来的，远程调用需要自己添加剂。
             */
            @Override
            public void apply(RequestTemplate template) {
                /**
                 * 远程调用新开的线程，已经将主线程的requestAttributes   set到了这个新开的线程，所以可以获取到requestAttributes。
                 * 进而从中获取到cookie。
                 */
                //1、使用RequestContextHolder拿到刚进来的请求数据
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (requestAttributes != null) {
                    //老请求
                    HttpServletRequest request = requestAttributes.getRequest();

                    if (request != null) {
                        //2、同步请求头的数据（主要是cookie）
                        //把老请求的cookie值放到新请求上来，进行一个同步
                        String cookie = request.getHeader("Cookie");
                        template.header("Cookie", cookie);
                    }
                }
            }
        };

        return requestInterceptor;
    }

}
