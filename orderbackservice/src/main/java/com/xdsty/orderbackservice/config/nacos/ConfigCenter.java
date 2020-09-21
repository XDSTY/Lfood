package com.xdsty.orderbackservice.config.nacos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张富华
 * @date 2020/7/28 16:00
 */
@Component
public class ConfigCenter implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ConfigCenter.class);

    private static List<Hook> hookList = new ArrayList<>();

    public static void addHook(Hook hook) {
        hookList.add(hook);
    }

    /**
     * 存放对应的配置
     */
    private static Map<String, String> configMaps = new ConcurrentHashMap<>();

    public static void setConfigKeyValue(String key, String value) {
        configMaps.put(key, value);
    }

    public static String getConfigValue(String key) {
        return configMaps.get(key);
    }

    /**
     * 项目启动时初始化配置中心值
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (ConfigKeyEnum config : ConfigKeyEnum.values()) {
            ConfigManager.addConfigListener(config.dataId, config.groupId, (value) -> {
                log.error("配置中心改动, dataId: {}, groupId: {}, 改动后值: {}", config.dataId, config.groupId, value);
                setConfigKeyValue(config.dataId, value);
                for (Hook hook : hookList) {
                    hook.hook(config.dataId, value);
                }
            });
        }
    }

}
