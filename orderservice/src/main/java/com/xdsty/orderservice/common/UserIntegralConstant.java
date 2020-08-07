package com.xdsty.orderservice.common;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 张富华
 * @date 2020/8/7 17:14
 */
public class UserIntegralConstant {

    /**
     * 用于标记当前已发送的记录id
     * -1：表示初始化状态  -2：表示已经有线程去查询数据库，其他线程应该等待
     */
    public static AtomicLong sendingFlag = new AtomicLong(-1);

}
