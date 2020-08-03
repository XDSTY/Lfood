package com.xdsty.orderservice.transaction;

public enum TransactionEnum {
    /**
     * transaction事务的状态
     */
    INIT(1, "prepare的初始化状态"),
    COMMITTED(2, "已提交"),
    ROLLBACK(3, "已回滚");

    public Integer status;

    public String desc;

    TransactionEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
