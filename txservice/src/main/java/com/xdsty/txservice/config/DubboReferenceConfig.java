package com.xdsty.txservice.config;

import com.xdsty.orderclient.service.OrderTxService;
import com.xdsty.productclient.service.StorageTxService;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 张富华
 * @date 2020/8/3 17:56
 */
@Configuration
public class DubboReferenceConfig {

    @Bean
    public ReferenceBean<OrderTxService> orderTxServiceReferenceBean() {
        ReferenceBean<OrderTxService> bean = new ReferenceBean<>();
        bean.setInterface(OrderTxService.class);
        bean.setVersion("1.0");
        bean.setTimeout(3000);
        bean.setRetries(0);
        return bean;
    }

    @Bean
    public ReferenceBean<StorageTxService> storageTxServiceReferenceBean() {
        ReferenceBean<StorageTxService> bean = new ReferenceBean<>();
        bean.setInterface(StorageTxService.class);
        bean.setVersion("1.0");
        bean.setTimeout(3000);
        bean.setRetries(0);
        return bean;
    }
}
