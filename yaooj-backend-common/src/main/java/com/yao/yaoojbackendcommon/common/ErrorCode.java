package com.yao.yaoojbackendcommon.common;

/**
 * ClassName: ErrorCode
 * Package: com.yao.yaoojbackendcommon.common
 * Description:
 * 自定义的错误码
 *
 * @Author Joy_瑶
 * @Create 2024/7/17 20:21
 * @Version 1.0
 */
public enum ErrorCode {
    PARAMS_ERROR(40000,"请求参数错误"),
    NOT_LOGIN_ERROR(40100,"未登录"),
    NO_AUTH_ERROR(40101,"无权限"),
    NOT_FOUND_ERROR(40400,"请求数据不存在"),
    FORBIDDEN_ERROR(40300,"禁止访问"),
    SYSTEM_ERROR(50000,"系统内部异常"),
    OPERATION_ERROR(50001,"操作失败");
    int code;
    String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
