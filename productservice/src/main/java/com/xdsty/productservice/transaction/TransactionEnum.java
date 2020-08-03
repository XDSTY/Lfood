package com.xdsty.productservice.transaction;

/**
 * @author 张富华
 * @date 2020/7/29 16:52
 */
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
