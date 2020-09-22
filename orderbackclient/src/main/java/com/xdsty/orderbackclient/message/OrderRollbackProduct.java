package com.xdsty.orderbackclient.message;


public class OrderRollbackProduct {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品数量
     */
    private Integer productNum;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }
}
