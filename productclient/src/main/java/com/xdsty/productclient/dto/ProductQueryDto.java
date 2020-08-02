package com.xdsty.productclient.dto;


import basecommon.util.Page;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/7/1 11:30
 */
public class ProductQueryDto extends Page implements Serializable {

    private static final long serialVersionUID = -8055076851909832899L;

    private Long cityId;

    private String productName;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
