package com.xdsty.orderservice.util;

import com.xdsty.orderservice.nacos.ConfigCenter;
import com.xdsty.orderservice.nacos.ConfigKeyEnum;

import java.util.List;

public final class ZSetListUtil {

    /**
     * 随机获取一个zset
     * @return
     */
    public static String random() {
        // 获取zset列表
        String zsetListJson = ConfigCenter.getConfigValue(ConfigKeyEnum.ORDER_BACK_ZSET_LIST_CONFIG.dataId);
        List<String> zsetList = JsonUtil.parseArrJson(zsetListJson, String.class);
        return zsetList.get((int) (Math.random() * zsetList.size()));
    }

}
