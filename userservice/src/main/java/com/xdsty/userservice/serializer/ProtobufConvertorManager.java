package com.xdsty.userservice.serializer;

import basecommon.serializer.AbstractProtobufConvertManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author 张富华
 * @date 2020/8/6 17:45
 */
@Configuration
public class ProtobufConvertorManager extends AbstractProtobufConvertManager implements ApplicationRunner {

    @Override
    public void initProtobufMapInfo() {

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initProtobufMapInfo();
    }
}
