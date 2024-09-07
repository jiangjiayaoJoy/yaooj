package com.yao.yaoojbackendcommon.exception;

import com.yao.yaoojbackendcommon.common.ErrorCode;
import com.yao.yaoojbackendcommon.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.yao.yaoojbackendcommon.exception
 * Description:
 * 全局异常处理器
 *
 * @Author Joy_瑶
 * @Create 2024/7/19 10:16
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> businessExceptionHandler(BusinessException e){
        log.error("BusinessException",e);
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException e){
        log.error("RuntimeException",e);
        return Result.error(ErrorCode.SYSTEM_ERROR,"系统错误");
    }
}
