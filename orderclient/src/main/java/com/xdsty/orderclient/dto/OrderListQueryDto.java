package com.xdsty.orderclient.dto;

import basecommon.util.Page;
import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/9/17 9:48
 */
public class OrderListQueryDto extends Page implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单状态
     */
    private Integer status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
