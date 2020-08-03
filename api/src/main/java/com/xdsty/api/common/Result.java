package com.xdsty.api.common;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class Result<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static Result failure(ResultCode code) {
        return new Result<>(code.getCode(), code.getMessage(), null);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<T>(ResultCode.BUSINESS_FAIL.getCode(), message, null);
    }

    public static <T> Result success(ResultCode code, T data) {
        return new Result<>(code.getCode(), code.getMessage(), data);
    }
}