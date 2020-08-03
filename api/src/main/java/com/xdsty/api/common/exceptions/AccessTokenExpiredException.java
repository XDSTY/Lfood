package com.xdsty.api.common.exceptions;

/**
 * accessToken过期异常
 *
 * @author 张富华
 * @date 2020/6/24 9:21
 */
public class AccessTokenExpiredException extends RuntimeException {

    public AccessTokenExpiredException(String message) {
        super(message);
    }

    public AccessTokenExpiredException(Throwable cause) {
        super(cause);
    }

}
