package com.xdsty.orderclient.re;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/9/17 9:50
 */
public class OrderListProductRe implements Serializable {

    /**
     * 商品Id
     */
    private Long productId;

    /**
     * 商品加上商品附加的价格
     */
    private BigDecimal totalPrice;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 订单商品下的附加项
     */
    private List<Long> additionalIds;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<Long> getAdditionalIds() {
        return additionalIds;
    }

    public void setAdditionalIds(List<Long> additionalIds) {
        this.additionalIds = additionalIds;
    }
}
