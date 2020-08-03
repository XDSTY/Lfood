package com.xdsty.api.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class RequestContextHolder {

    public static HttpServletRequest getServletRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getServletResponse() {
        return getServletRequestAttributes().getResponse();
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
    }

}
