package com.xdsty.userclient.re;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/9/15 17:58
 */
public class UserIntegralRe implements Serializable {

    /**
     * 积分
     */
    private Integer integral;

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}
