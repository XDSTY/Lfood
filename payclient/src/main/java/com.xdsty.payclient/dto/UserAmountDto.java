package com.xdsty.payclient.dto;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/9/15 18:07
 */
public class UserAmountDto implements Serializable {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
