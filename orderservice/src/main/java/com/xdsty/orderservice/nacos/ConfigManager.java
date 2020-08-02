package com.xdsty.orderservice.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 张富华
 * @date 2020/7/28 14:59
 */
@Component
public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);

    private static final long DEFAULT_TIMEOUT = 3000L;

    private static ConfigService configService;

    @NacosInjected
    public void setConfigService(ConfigService configService) {
        ConfigManager.configService = configService;
    }

    /**
     * 添加配置中心监听器
     * @param dataId key
     * @param groupId groupId
     * @param listener 监听器
     */
    public static void addConfigListener(String dataId, String groupId, NacosConfigListener listener) {
        String configValue;
        try {
            configValue = configService.getConfig(dataId, groupId, DEFAULT_TIMEOUT);
            log.error("初始化配置中心项，dataId: {}, groupId: {}, cofigValue: {}", dataId, groupId, configValue);
            if(StringUtils.isNotEmpty(configValue)) {
                ConfigCenter.setConfigKeyValue(dataId, configValue);
            }
            configService.addListener(dataId, groupId, listener);
        } catch (NacosException e) {
            log.error("获取配置中心失败, dataId: {}", dataId, e);
        }
    }

}
