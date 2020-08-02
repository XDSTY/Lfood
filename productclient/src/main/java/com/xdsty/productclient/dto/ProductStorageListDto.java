package com.xdsty.productclient.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/29 10:54
 */
public class ProductStorageListDto implements Serializable {

    private static final long serialVersionUID = 1327798728639269779L;

    private Long userId;

    private List<ProductStorageDto> productStorageList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ProductStorageDto> getProductStorageDtos() {
        return productStorageList;
    }

    public void setProductStorageDtos(List<ProductStorageDto> productStorageDtos) {
        this.productStorageList = productStorageDtos;
    }
}
