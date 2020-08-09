package com.xdsty.orderservice.mq;

import com.xdsty.orderservice.BaseTest;
import com.xdsty.orderservice.common.Constant;
import com.xdsty.userclient.message.UserIntegralMessage;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * @author 张富华
 * @date 2020/8/7 15:01
 */
public class MqSenderTest extends BaseTest {

    @Test
    public void send() {
        MqSender.send(Constant.USER_INTEGRAL_TOPIC, "asdfg");
    }
}