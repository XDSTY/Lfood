package com.xdsty.api.config.jwt;

import basecommon.exception.BusinessRuntimeException;
import com.xdsty.api.common.ApplicationContextHolder;
import com.xdsty.api.common.Constant;
import com.xdsty.api.entity.UserTokenInfo;
import com.xdsty.api.util.Base64Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Key;
import java.util.Date;

/**
 * @author 张富华
 * @date 2020/6/16 9:42
 */
public class JwtTokenUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static Audience audience;

    static {
        audience = (Audience) ApplicationContextHolder.getApplicationContext().getBean("audience");
    }

    /**
     * 生成jwt token
     *
     * @param userInfo      用户信息
     * @param isAccessToken 是否是accessToken
     * @return token
     */
    private static String createToken(UserTokenInfo userInfo, boolean isAccessToken) {
        try {
            SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
            byte[] secretBytes = DatatypeConverter.parseBase64Binary(audience.getSecret());
            Key signKey = new SecretKeySpec(secretBytes, algorithm.getJcaName());

            long currentTimeMills = System.currentTimeMillis();
            Date now = new Date(currentTimeMills);

            String userIdEncry = Base64Util.encode(String.valueOf(userInfo.getUserId()));
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    // 存放一些信息
                    .claim("userId", userIdEncry)
                    .claim("origin", userInfo.getOrigin())
                    // jwt的签发主体
                    .setIssuer(audience.getClientId())
                    // jwt的签发时间
                    .setIssuedAt(now)
                    // jwt的接受对象
                    .setAudience(audience.getName())
                    .signWith(algorithm, signKey);

            long ttlMills = isAccessToken ? Long.parseLong(audience.getAccessTokenExpiredMills()) : Long.parseLong(audience.getRefreshTokenExpiredMills());
            if (ttlMills > 0) {
                long expiredMills = currentTimeMills + ttlMills;
                Date expiredDate = new Date(expiredMills);
                // token的过期时间
                builder.setExpiration(expiredDate)
                        // 生效时间
                        .setNotBefore(now);
            }
            return builder.compact();
        } catch (Exception e) {
            log.error("签发token失败", e);
            throw new BusinessRuntimeException("登陆失败");
        }
    }

    /**
     * 创建accessToken  有效期为10 min
     *
     * @param userTokenInfo 用户信息
     * @return
     */
    public static String createAccessToken(UserTokenInfo userTokenInfo) {
        return Constant.TOKEN_PREFIX + createToken(userTokenInfo, true);
    }

    /**
     * 创建refreshToken  有效期为7 day
     *
     * @param userTokenInfo 用户信息
     * @return
     */
    public static String createRefreshToken(UserTokenInfo userTokenInfo) {
        return createToken(userTokenInfo, false);
    }

    /**
     * 解析token
     *
     * @param token token
     * @return
     */
    public static Claims parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(audience.getSecret()))
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}