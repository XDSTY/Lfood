package com.xdsty.userclient.enums;

/**
 * @author 张富华
 * @date 2020/6/17 14:53
 */
public enum UserStatusEnum {
    /**
     * 正常，冻结
     */
    NORMAL(1, "正常"), FREEZE(0, "冻结");

    private Integer value;

    private String desc;

    UserStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
