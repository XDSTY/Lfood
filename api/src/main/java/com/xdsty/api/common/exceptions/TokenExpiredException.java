package com.xdsty.api.common.exceptions;

/**
 * refreshToken过期异常
 *
 * @author 张富华
 * @date 2020/6/24 9:30
 */
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }

    public TokenExpiredException(Throwable cause) {
        super(cause);
    }

}
