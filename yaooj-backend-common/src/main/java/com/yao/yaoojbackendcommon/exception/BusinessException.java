package com.yao.yaoojbackendcommon.exception;


import com.yao.yaoojbackendcommon.common.ErrorCode;

/**
 * ClassName: BusinessException
 * Package: com.yao.yaoojbackendcommon.exception
 * Description:
 * 自定义业务异常类
 *
 * @Author Joy_瑶
 * @Create 2024/7/19 10:12
 * @Version 1.0
 */
public class BusinessException extends RuntimeException{
    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code,String message){
        super(message);
        this.code=code;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code=errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode,String message){
        super(message);
        this.code= errorCode.getCode();
    }

    public int getCode(){
        return code;
    }
}
