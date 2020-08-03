package com.xdsty.api.controller.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/7/2 13:58
 */
@Getter
@Setter
public class TokenParam {

    private String accessToken;

    private String refreshToken;

}
