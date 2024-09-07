package com.yao.yaoojbackendcommon.common;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: Result
 * Package: com.yao.yaoojbackendcommon.common
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/7/17 20:36
 * @Version 1.0
 */
@Data
public class Result<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public Result(int code, T data, String message) {
        this.code=code;
        this.data=data;
        this.message=message;
    }

    public static<T> Result<T> success(){
        return new Result<>(0,null,"ok");
    }

    public static<T> Result<T> success(T data){
        return new Result<>(0,data,"ok");
    }

    public static<T> Result<T> error(ErrorCode errorCode){
        return new Result<>(errorCode.getCode(),null,errorCode.getMessage());
    }
    public static<T> Result<T> error(int code,String message){
        return new Result<>(code,null,message);
    }
    public static<T> Result<T> error(ErrorCode errorCode,String message){
        return new Result<>(errorCode.getCode(),null,message);

    }
}
