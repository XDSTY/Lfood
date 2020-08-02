package com.xdsty.productservice.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/7/29 14:33
 */
@Getter
@Setter
public class ProductStorage {

    private Long id;

    private Long productId;

    /**
     * 总库存数量
     */
    private Integer totalQty;

    /**
     * 可用库存数量
     */
    private Integer availableQty;

    /**
     * 锁定的库存数量
     */
    private Integer lockedQty;

    public ProductStorage() {
    }

    public ProductStorage(Long productId, Integer lockedQty) {
        this.productId = productId;
        this.lockedQty = lockedQty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }

    public Integer getLockedQty() {
        return lockedQty;
    }

    public void setLockedQty(Integer lockedQty) {
        this.lockedQty = lockedQty;
    }
}
