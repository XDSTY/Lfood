package com.xdsty.productclient.re;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/9/17 16:46
 */
public class OrderProductRe implements Serializable {

    private Long productId;

    private String productName;

    private String thumbnail;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
