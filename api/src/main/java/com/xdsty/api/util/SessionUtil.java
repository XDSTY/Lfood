package com.xdsty.api.util;


import com.xdsty.api.common.UserSession;

public final class SessionUtil {

    private static ThreadLocal<UserSession> userSessionThreadLocal = new ThreadLocal<>();

    public static void setUserSessionThreadLocal(UserSession userSession) {
        userSessionThreadLocal.set(userSession);
    }

    public static UserSession getUserSession() {
        return userSessionThreadLocal.get();
    }

    public static Long getUserId() {
        UserSession session = getUserSession();
        return session == null ? 1L : session.getUserId();
    }
}
