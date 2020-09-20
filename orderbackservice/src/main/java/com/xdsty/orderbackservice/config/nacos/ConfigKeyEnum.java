package com.xdsty.orderbackservice.config.nacos;
import com.xdsty.orderbackservice.util.Constant;

/**
 * @author 张富华
 * @date 2020/7/28 16:02
 */
public enum ConfigKeyEnum {

    /**
     * 订单回退的zset列表
     */
    ORDER_BACK_ZSET_LIST_CONFIG("redis.zset.list", Constant.COMMON_CENTER_GROUPID);

    public String dataId;

    public String groupId;

    ConfigKeyEnum(String dataId, String groupId) {
        this.dataId = dataId;
        this.groupId = groupId;
    }
}
