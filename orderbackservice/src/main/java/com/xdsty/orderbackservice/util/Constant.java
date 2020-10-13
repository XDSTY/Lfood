package com.xdsty.orderbackservice.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class Constant {

    /**
     * 公共配置命名空间
     */
    public static String COMMON_CENTER_GROUPID = "COMMON_CONF_GROUP";

    /**
     * 代付款订单保存到数据库中的前缀  order_back_{orderId}
     */
    public static final String ORDER_BACK_PREFIX = "order_back_";

    /**
     * redis分布式锁前缀
     */
    public static final String REDIS_LOCK_PREFIX = "redis_lock_prefix";

    /**
     * 订单回滚topic
     */
    public static final String ORDER_ROLLBACK_TOPIC = "order-rollback";

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        list.add(12);
//这里直接添加会报错
        Class<? extends List> clazz = list.getClass();
        Method add = null;
        try {
            add = clazz.getDeclaredMethod("add", Object.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
//但是通过反射添加，是可以的
        try {
            add.invoke(list, "kl");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println(list);
    }
}
