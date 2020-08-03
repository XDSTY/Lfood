package com.xdsty.api.common;

/**
 * @author 张富华
 * @date 2020/6/23 16:34
 */
public final class Constant {

    /**
     * redis session前缀
     */
    public static final String REDIS_TOKEN_PREFIX = "api_";

    public static final String REDIS_USER_PREFIX = "api_user_";

    public static final long REDIS_USER_TTL = 604800000L;

    /**
     * redis token hash过期时间
     */
    public static final long REDIS_TOKEN_TTL = 604800000L;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String ACCESS_TOKEN = "authorization";

    public static final String REFRESH_TOKEN = "refreshtoken";

    public static final String SOURCE_CODE = "sourceCode";

}
