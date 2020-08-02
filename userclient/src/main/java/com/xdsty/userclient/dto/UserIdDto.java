package com.xdsty.userclient.dto;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/7/1 17:44
 */
public class UserIdDto implements Serializable {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
