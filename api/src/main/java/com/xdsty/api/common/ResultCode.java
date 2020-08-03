package com.xdsty.api.common;

public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(1, ""),
    /**
     * 业务错误
     */
    BUSINESS_FAIL(-1, ""),
    PARAM_INVALID(1001, "参数无效"),
    PARAM_NOT_COMPLETE(1002, "参数缺失"),
    LOGIN_ERROR(1010, "用户名或密码错误"),
    NEED_LOGIN(1011, "需要登录"),
    USER_NOT_EXIST(1012, "用户不存在"),
    SIGNATURE(1022, "重新签发token"),
    SIGNATURE_ERROR(1023, "签发token失败"),
    SIGNATURE_SUCCESS(1024, "签发token成功");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}