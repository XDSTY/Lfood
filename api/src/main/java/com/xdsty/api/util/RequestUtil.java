package com.xdsty.api.util;

/**
 * @author 张富华
 * @date 2020/6/23 16:17
 */
public final class RequestUtil {

    public static String getHeader(String header) {
        return RequestContextHolder.getServletRequest().getHeader(header);
    }

}
