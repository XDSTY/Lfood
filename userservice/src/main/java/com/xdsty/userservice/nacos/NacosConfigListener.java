package com.xdsty.userservice.nacos;

import com.alibaba.nacos.api.config.listener.Listener;
import java.util.concurrent.Executor;

/**
 * @author 张富华
 * @date 2020/7/28 14:42
 */
public interface NacosConfigListener extends Listener {

    /**
     * 实现默认的方法
     *
     * @return 返回空
     */
    @Override
    default Executor getExecutor() {
        return null;
    }

}
