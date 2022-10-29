package com.han.gulimall.auth.feign;

import com.han.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author xsh
 */
@FeignClient("gulimall-third-part")
public interface ThirdPartyFeignService {
    @GetMapping("/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
