package com.xdsty.api.controller.content;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/7/2 13:57
 */
@Getter
@Setter
public class TokenContent {

    private String accessToken;

    private String refreshToken;

}
