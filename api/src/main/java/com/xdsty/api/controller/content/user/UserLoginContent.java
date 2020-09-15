package com.xdsty.api.controller.content.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/6/16 14:15
 */
@Getter
@Setter
public class UserLoginContent {

    private String accessToken;

    private String refreshToken;

}