package com.xdsty.orderservice.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.Executor;

/**
 * @author 张富华
 * @date 2020/7/22 15:52
 */
public abstract class AbstractConfigInfo<T> implements InitializingBean, Listener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @NacosInjected
    private ConfigService configService;

    protected T data;

    public T getData() {
        return data;
    }

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        logger.info("{}#receiveConfigInfo receive configInfo. configInfo={}", getClass().getSimpleName(), configInfo);
        compile(configInfo);
    }

    @Override
    public void afterPropertiesSet() throws RuntimeException {
        try {
            String configInfo = configService.getConfig(getDataId(), getGroupId(), getTimeout());
            logger.info("{}#afterPropertiesSet init configInfo. configInfo={}", getClass().getSimpleName(), configInfo);
            compile(configInfo);
            configService.addListener(getDataId(), getGroupId(), this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取nacos dataId
     *
     * @return nacos dataId
     */
    protected abstract String getDataId();

    /**
     * 获取nacos groupId
     * <p>默认NacosConstant.GROUP_ID</p>
     *
     * @return nacos groupId
     */
    protected String getGroupId() {
        return "ORDER_SERVICE_GROUP";
    }

    /**
     * 获取nacos 超时时间
     * <p>默认NacosConstant.TIMEOUT</p>
     *
     * @return nacos 超时时间
     */
    protected long getTimeout() {
        return 3000L;
    }

    /**
     * 将输入转为数据
     *
     * @param dataStr
     */
    protected abstract void compile(String dataStr);

}
