package com.xdsty.orderservice.config;

import com.xdsty.productclient.service.StorageTxService;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReferenceConfig {

    @Bean
    public ReferenceBean<StorageTxService> serviceReferenceBean() {
        ReferenceBean<StorageTxService> bean = new ReferenceBean<>();
        bean.setVersion("1.0");
        bean.setInterface(StorageTxService.class);
        bean.setTimeout(3000);
        return bean;
    }

}
