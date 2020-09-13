package com.xdsty.api.controller.content.cart;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/15 15:17
 */
@Getter
@Setter
public class CartItemContent {

    /**
     * 购物车id
     */
    private Long cartId;

    /**
     * 购物车商品
     */
    private Long productId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 总价
     */
    private String totalPrice;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 商品缩略图
     */
    private String thumbnail;

    /**
     * 商品加上商品的附加的价格
     */
    private String price;

    /**
     * 单个商品的价格
     */
    private String productPrice;

    /**
     * 购物车商品所选择的附加项
     */
    private List<CartAdditionalItemContent> cartAdditionalItems;

}
