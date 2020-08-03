package com.xdsty.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/6/23 17:04
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tokens {

    private String accessToken;

    private String refreshToken;

}
