package com.xdsty.api.config.interctptor;

import com.xdsty.api.common.Constant;
import com.xdsty.api.common.UserSession;
import com.xdsty.api.common.exceptions.AccessTokenExpiredException;
import com.xdsty.api.common.exceptions.TokenExpiredException;
import com.xdsty.api.config.jwt.JwtTokenUtil;
import com.xdsty.api.util.Base64Util;
import com.xdsty.api.util.RedisUtil;
import com.xdsty.api.util.RequestUtil;
import com.xdsty.api.util.SessionUtil;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Date;

/**
 * jwt校验拦截器
 *
 * @author 张富华
 * @date 2020/6/16 11:22
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        return handle(request);
    }

    /**
     * 0、accessToken为空或者accessToken无效，重新登录 TokenExpiredException
     * 1、accessToken有效且未过期，放行
     * 3、accessToken过期，refreshToken为空，要求前端携带refreshToken重新请求   AccessTokenExpiredException
     * 4、accessToken过期，refreshToken过期或失效，重新登录   TokenExpiredException
     * 5、accessToken过期，refreshToken未过期，生成新的accessToken和refreshToken并放入request里面，ResponseAdvice里面进行判断
     */
    private boolean handle(HttpServletRequest request) {
        String authHeader = request.getHeader(Constant.ACCESS_TOKEN);
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(Constant.TOKEN_PREFIX)) {
            throw new TokenExpiredException("重新登录");
        }

        String accessToken = authHeader.substring(7);
        Claims claims = JwtTokenUtil.parseToken(accessToken);
        long userId = Long.parseLong(Base64Util.decode(claims.get("userId", String.class)));
        String origin = RequestUtil.getHeader(Constant.SOURCE_CODE);
        String tokenRedisKey = Constant.REDIS_TOKEN_PREFIX + origin + "_" + userId;
        // accessToken无效
        if (!RedisUtil.sexist(tokenRedisKey, authHeader)) {
            log.error("redis中accessToken失效,key: {}", tokenRedisKey);
            throw new TokenExpiredException(userId + "accessToken失效，重新登录");
        }
        // accessToken过期
        if (claims.getExpiration().before(new Date())) {
            throw new AccessTokenExpiredException(userId + "accessToken过期，请携带refreshToken请求");
        }
        // 获取用户的session
        Object obj = RedisUtil.get(Constant.REDIS_USER_PREFIX + userId);
        if (obj == null) {
            throw new TokenExpiredException(userId + " session为空，重新登录");
        }
        SessionUtil.setUserSessionThreadLocal((UserSession) obj);
        return true;
    }

}