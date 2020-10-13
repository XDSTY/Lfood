package com.xdsty.api.controller.aop;

import basecommon.exception.BusinessRuntimeException;
import com.xdsty.api.common.Constant;
import com.xdsty.api.controller.param.order.OrderAddParam;
import com.xdsty.api.util.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @author 张富华
 * @date 2020/10/13 16:13
 */
//@Component
//@Aspect
public class OrderAspect {


    @Pointcut("execution(public com.xdsty.api.controller.OrderController.placeOrder(..)))")
    public void OrderAspect() {
    }

    @Before("OrderAspect()")
    public void before(JoinPoint joinPoint) {
        Object param0 = joinPoint.getArgs()[0];
        if (!(param0 instanceof OrderAddParam)) {
            return;
        }
        OrderAddParam param = (OrderAddParam) param0;
        // 防止重复下单key
        String distinctKey = getOrderDistinctKey(param);
        if (!RedisUtil.setnx(distinctKey, "", Constant.ORDER_DISTINCT_KEY_TTL, TimeUnit.MILLISECONDS)) {
            throw new BusinessRuntimeException("后台处理中，请不要重复操作");
        }
    }

    @AfterThrowing(value = "OrderAspect()", throwing = "ex")
    public void afterThrow(JoinPoint joinPoint, Exception ex) {

    }

    private String getOrderDistinctKey(OrderAddParam param) {
        return Constant.ORDER_DISTINCT_PREFIX + param.getUserId() + "_" + param.getUniqueRow();
    }

}
