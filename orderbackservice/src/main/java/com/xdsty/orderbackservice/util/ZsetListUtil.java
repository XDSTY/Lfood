package com.xdsty.orderbackservice.util;

import com.xdsty.orderbackservice.config.nacos.ConfigCenter;
import com.xdsty.orderbackservice.config.nacos.ConfigKeyEnum;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 张富华
 * @date 2020/9/21 10:52
 */
public class ZsetListUtil {

    /**
     * 当前的zset列表
     */
    public volatile static List<String> zsetList =
            JsonUtil.parseArrJson(ConfigCenter.getConfigValue(ConfigKeyEnum.ORDER_BACK_ZSET_LIST_CONFIG.dataId), String.class);

    /**
     * 随机选取一个zset
     * @return
     */
    public static String random() {
        // TODO 并发问题
        if(CollectionUtils.isEmpty(zsetList)) {
            return null;
        }
        return zsetList.get((int) (Math.random() * zsetList.size()));
    }
}
