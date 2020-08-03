package com.xdsty.orderclient.dto;


import basecommon.util.Page;

import java.io.Serializable;

public class CartItemListDto extends Page implements Serializable {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
