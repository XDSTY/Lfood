package com.xdsty.productclient.service;

import com.xdsty.productclient.dto.ProductStorageListDto;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author 张富华
 * @date 2020/7/29 10:45
 */
public interface StorageTxService {

    /**
     * 冻结库存接口
     *
     * @param context 事务上下文
     * @param dto     入参
     * @return
     */
    @TwoPhaseBusinessAction(name = "prepare", commitMethod = "commit", rollbackMethod = "rollback")
    boolean prepare(BusinessActionContext context, @BusinessActionContextParameter(paramName = "dto") ProductStorageListDto dto);

    /**
     * 事务提交接口
     *
     * @param context 事务上下文
     * @return
     */
    boolean commit(BusinessActionContext context);

    /**
     * 回滚接口
     *
     * @param context 事务上下文
     * @return
     */
    boolean rollback(BusinessActionContext context);

}
