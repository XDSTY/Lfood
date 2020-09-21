package com.xdsty.orderbackservice.config.nacos;

/**
 * 配置中心变动处理
 * @author 张富华
 * @date 2020/9/21 11:04
 */
public interface Hook {

    void hook(String configKey, String newValue);

}
