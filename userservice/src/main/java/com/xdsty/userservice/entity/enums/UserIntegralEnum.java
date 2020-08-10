package com.xdsty.userservice.entity.enums;

/**
 * @author 张富华
 * @date 2020/8/10 10:42
 */
public enum  UserIntegralEnum {
    INIT(0, "初始化"),
    SEND(1, "已完成");

    public Integer status;

    public String desc;

    UserIntegralEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
