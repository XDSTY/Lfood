package com.xdsty.api.controller.content.user;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 个人中心用户信息
 * @author 张富华
 * @date 2020/9/15 17:48
 */
@Getter
@Setter
public class UserInfoContent {

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String profilePic;

    /**
     * 账户余额
     */
    private BigDecimal amount;

    /**
     * 积分
     */
    private Integer integral;

}
