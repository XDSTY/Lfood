package com.xdsty.productclient.re;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/6/8 13:49
 */
public class ProductDetailRe implements Serializable {

    private static final long serialVersionUID = -136239170606296070L;

    private Long id;

    private String productName;

    private Boolean limitNum;

    private Integer totalNum;

    private Integer saledNum;

    private Integer remainingNum;

    private Date cutOffTime;

    private BigDecimal price;

    private String thumbnail;

    private List<String> bannerList;

    private List<AdditionalItemRe> additionalItemRes;

    private List<String> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Boolean limitNum) {
        this.limitNum = limitNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getSaledNum() {
        return saledNum;
    }

    public void setSaledNum(Integer saledNum) {
        this.saledNum = saledNum;
    }

    public Integer getRemainingNum() {
        return remainingNum;
    }

    public void setRemainingNum(Integer remainingNum) {
        this.remainingNum = remainingNum;
    }

    public Date getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Date cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<String> bannerList) {
        this.bannerList = bannerList;
    }

    public List<AdditionalItemRe> getAdditionalItemRes() {
        return additionalItemRes;
    }

    public void setAdditionalItemRes(List<AdditionalItemRe> additionalItemRes) {
        this.additionalItemRes = additionalItemRes;
    }
}
