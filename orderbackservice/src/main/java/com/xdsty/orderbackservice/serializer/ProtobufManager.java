package com.xdsty.orderbackservice.serializer;

import basecommon.serializer.AbstractProtobufConvertManager;
import com.xdsty.orderbackclient.serializer.OrderBackMessageProto;
import com.xdsty.orderbackclient.serializer.OrderRollBackProductMessageProto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class ProtobufManager extends AbstractProtobufConvertManager implements ApplicationRunner {

    @Override
    public void initProtobufMapInfo() {
        putParseClassMap(OrderBackMessageProto.OrderRollBackMessage.class.getSimpleName(), OrderBackMessageProto.OrderRollBackMessage.class.getName());
        putParseClassMap(OrderRollBackProductMessageProto.OrderRollBackProductMessage.class.getSimpleName(), OrderRollBackProductMessageProto.OrderRollBackProductMessage.class.getName());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initProtobufMapInfo();
    }
}
