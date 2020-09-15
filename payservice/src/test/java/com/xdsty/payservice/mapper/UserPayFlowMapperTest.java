package com.xdsty.payservice.mapper;

import com.xdsty.payservice.entity.UserPayFlow;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;

/**
 * @author 张富华
 * @date 2020/9/15 16:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPayFlowMapperTest {

    @Resource
    private UserPayFlowMapper mapper;

    @Test
    public void test() {
        UserPayFlow userPayFlow = new UserPayFlow();
        userPayFlow.setUserId(1L);
        userPayFlow.setPayType(1);
        userPayFlow.setPayChannel(1);
        userPayFlow.setAmount(new BigDecimal("10"));
        mapper.insertOne(userPayFlow);
    }


}