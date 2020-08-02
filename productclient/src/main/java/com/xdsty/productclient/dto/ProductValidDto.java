package com.xdsty.productclient.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/16 10:40
 */
public class ProductValidDto implements Serializable {

    private static final long serialVersionUID = 4001110075608064893L;

    private Long productId;

    private List<Long> productAdditionalIds;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<Long> getProductAdditionalIds() {
        return productAdditionalIds;
    }

    public void setProductAdditionalIds(List<Long> productAdditionalIds) {
        this.productAdditionalIds = productAdditionalIds;
    }
}
