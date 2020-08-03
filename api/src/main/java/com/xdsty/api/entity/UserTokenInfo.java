package com.xdsty.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * token中存放的用户信息信息
 *
 * @author 张富华
 * @date 2020/6/16 10:57
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenInfo {

    private Long userId;

    private Integer origin;

}