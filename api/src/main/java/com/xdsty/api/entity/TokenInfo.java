package com.xdsty.api.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * token中存放的所有信息
 *
 * @author 张富华
 * @date 2020/6/16 11:06
 */
@Getter
@Setter
public class TokenInfo {

    /**
     * 用户信息
     */
    private UserTokenInfo userInfo;

    /**
     * jwt的持有人
     */
    private String subject;

    /**
     * 签发人
     */
    private String issuer;

    /**
     * 签发时间
     */
    private Date issuedAt;

    /**
     * 过期时间
     */
    private Date expiration;

    /**
     * 生效时间
     */
    private Date notBefore;

}