package com.han.gulimall.coupon;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @PACKAGE_NAME: com.han.gulimall.coupon
 * @Author XSH
 * @Date 2022-11-02 16:08
 * @Version 1.0.0
 * @Description：
 **/
public class Test01 {

    @Test
    public void test(){

//        LocalDate now = LocalDate.now();
//        LocalTime min = LocalTime.MIN;
//        LocalDateTime start = LocalDateTime.of(now,min);
//        String format = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusDays(2);
        LocalTime min = LocalTime.MAX;
        LocalDateTime end = LocalDateTime.of(localDate,min);
        String format = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);

    }


}
