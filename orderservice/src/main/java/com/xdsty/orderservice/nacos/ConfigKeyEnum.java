package com.xdsty.orderservice.nacos;


import com.xdsty.orderservice.common.Constant;

/**
 * @author 张富华
 * @date 2020/7/28 16:02
 */
public enum ConfigKeyEnum {

    /**
     * 雪花算法的开始的时间戳
     */
    SNOW_FLACK_TIMESTAMP("snow.flake.start.timestamp", Constant.CONF_CENTER_GROUPID),

    /**
     * 订单回退的zset列表
     */
    ORDER_BACK_ZSET_LIST_CONFIG("redis.zset.list", Constant.COMMON_CENTER_GROUPID),

    /**
     * 待支付订单持续时间
     */
    ORDER_WAIT_PAY_TIME("order.wait.pay.time", Constant.CONF_CENTER_GROUPID);

    public String dataId;

    public String groupId;

    ConfigKeyEnum(String dataId, String groupId) {
        this.dataId = dataId;
        this.groupId = groupId;
    }
}
