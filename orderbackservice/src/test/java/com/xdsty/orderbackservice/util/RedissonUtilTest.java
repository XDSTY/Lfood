package com.xdsty.orderbackservice.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 张富华
 * @date 2020/9/25 11:22
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedissonUtilTest {

    @Test
    public void testLock() {
        RLock lock = RedissonUtil.getClient().getLock("sss");
        lock.lock();
    }

}