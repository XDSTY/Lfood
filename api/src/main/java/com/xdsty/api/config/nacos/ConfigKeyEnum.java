package com.xdsty.api.config.nacos;


import com.xdsty.api.common.Constant;

/**
 * @author 张富华
 * @date 2020/7/28 16:02
 */
public enum ConfigKeyEnum {

    /**
     * 多少金额 = 1积分
     */
    INTEGRAL_CONFIG("integral.config", Constant.CONF_CENTER_GROUPID);

    public String dataId;

    public String groupId;

    ConfigKeyEnum(String dataId, String groupId) {
        this.dataId = dataId;
        this.groupId = groupId;
    }
}
