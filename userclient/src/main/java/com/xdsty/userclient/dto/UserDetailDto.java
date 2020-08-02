package com.xdsty.userclient.dto;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/6/17 14:44
 */
public class UserDetailDto implements Serializable {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
