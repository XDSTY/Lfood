package com.xdsty.orderservice.entity.enums;

public enum UserIntegralEnum {
    INIT(0, "初始化"),
    SEND(1, "已发送");

    public Integer status;

    public String desc;

    UserIntegralEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
