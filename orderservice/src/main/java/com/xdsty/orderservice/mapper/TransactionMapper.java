package com.xdsty.orderservice.mapper;

import com.xdsty.orderservice.transaction.Transaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionMapper {

    /**
     * 添加事务
     *
     * @param transaction 事务
     * @return
     */
    int insertTransaction(Transaction transaction);

    /**
     * 修改事务状态  提交或者回滚事务
     *
     * @param transaction
     * @return
     */
    int updateTransaction(Transaction transaction);

    /**
     * 查询事务记录
     *
     * @param xid      事务id
     * @param branchId 分支事务id
     * @return
     */
    Transaction getTransaction(@Param("xid") String xid, @Param("branchId") long branchId);

}