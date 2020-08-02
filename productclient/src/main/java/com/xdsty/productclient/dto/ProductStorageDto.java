package com.xdsty.productclient.dto;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/7/29 11:12
 */
public class ProductStorageDto implements Serializable {
    private static final long serialVersionUID = -7765991105174236936L;

    private Long productId;

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
