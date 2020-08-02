package com.xdsty.productclient.re;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/14 10:59
 */
public class CartItemProductRe implements Serializable {

    private static final long serialVersionUID = -6851267947516579212L;

    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private String thumbnail;

    private List<AdditionalItemRe> additionalItemRes;

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

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<AdditionalItemRe> getAdditionalItemRes() {
        return additionalItemRes;
    }

    public void setAdditionalItemRes(List<AdditionalItemRe> additionalItemRes) {
        this.additionalItemRes = additionalItemRes;
    }
}
