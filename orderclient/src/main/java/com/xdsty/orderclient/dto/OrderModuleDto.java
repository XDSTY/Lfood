package com.xdsty.orderclient.dto;

import java.io.Serializable;

public class OrderModuleDto implements Serializable {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
