package com.xdsty.orderclient.re;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/9/15 15:09
 */
public class OrderValidRe implements Serializable {

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
