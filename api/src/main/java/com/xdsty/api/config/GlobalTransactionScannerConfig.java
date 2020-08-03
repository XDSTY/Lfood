package com.xdsty.api.config;

import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 张富华
 * @date 2020/8/3 16:38
 */
@Configuration
public class GlobalTransactionScannerConfig {

    @Value("${project.name}")
    private String projectName;

    @Value("${seata.service-group-name}")
    private String exServiceGroup;

    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner(projectName, exServiceGroup);
    }

}
