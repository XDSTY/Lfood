package com.xdsty.api.util;

/**
 * @author 张富华
 * @date 2020/6/24 10:49
 */
public final class ResponseUtil {

    public static void setHeader(String header, String val) {
        RequestContextHolder.getServletResponse().setHeader(header, val);
    }

    public static String getHeader(String header) {
        return RequestContextHolder.getServletResponse().getHeader(header);
    }

}
