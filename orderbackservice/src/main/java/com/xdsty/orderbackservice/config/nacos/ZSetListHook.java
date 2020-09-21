package com.xdsty.orderbackservice.config.nacos;

import com.xdsty.orderbackservice.util.JsonUtil;
import com.xdsty.orderbackservice.util.RedisUtil;
import com.xdsty.orderbackservice.util.ZsetListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * 配置中心zset list变动hook
 * @author 张富华
 * @date 2020/9/21 11:06
 */
public class ZSetListHook implements Hook, ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ZSetListHook.class);

    @Override
    public void hook(String configKey, String newValue) {
        if (configKey.equals(ConfigKeyEnum.ORDER_BACK_ZSET_LIST_CONFIG.dataId)) {
            log.error("{}变动{}", configKey, this);
            ZsetListUtil.zsetList = JsonUtil.parseArrJson(newValue, String.class);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ConfigCenter.addHook(this);
    }
}
