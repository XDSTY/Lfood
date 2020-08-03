package com.xdsty.orderclient.service;

import com.xdsty.orderclient.dto.OrderAddDto;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author 张富华
 * @date 2020/7/28 9:46
 */
public interface OrderTxService {

    /**
     * 下单分布式事务，tcc的prepare阶段
     *
     * @param context     事务上下文
     * @param orderAddDto 订单数据
     * @return 是否操作成功
     */
    @TwoPhaseBusinessAction(name = "OrderTxService", commitMethod = "commit", rollbackMethod = "rollback")
    long prepare(BusinessActionContext context,
                 @BusinessActionContextParameter(paramName = "orderAddDto") OrderAddDto orderAddDto);

    /**
     * 下单tcc分布式事务，commit阶段
     *
     * @param context 事务上下文
     * @return
     */
    boolean commit(BusinessActionContext context);

    /**
     * 下单分布式事务，cancel
     *
     * @param context 事务上下文
     * @return
     */
    boolean rollback(BusinessActionContext context);

}
