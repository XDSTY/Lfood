package com.xdsty.api.common.exceptions;

/**
 * @author 张富华
 * @date 2020/6/23 9:59
 */
public class ApiRuntimeException extends RuntimeException {

    public ApiRuntimeException(String message) {
        super(message);
    }

    public ApiRuntimeException(Throwable cause) {
        super(cause);
    }

}
