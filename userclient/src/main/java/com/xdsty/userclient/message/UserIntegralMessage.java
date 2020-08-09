package com.xdsty.userclient.message;

/**
 * @author 张富华
 * @date 2020/8/6 18:15
 */
public class UserIntegralMessage {

    private Long id;

    private Long userId;

    private Long orderId;

    private Integer integral;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
