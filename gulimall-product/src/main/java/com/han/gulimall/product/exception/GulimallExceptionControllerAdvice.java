package com.han.gulimall.product.exception;

import com.han.gulimall.common.exception.BizCodeEnum;
import com.han.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xsh
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.han.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {


    /**
     * 统一异常处理，处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题：{}，异常类型: {}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError -> {
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        }));
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(),BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data",errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("错误:", throwable);
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(),BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
