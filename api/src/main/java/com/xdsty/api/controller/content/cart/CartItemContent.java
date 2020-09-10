package com.xdsty.api.controller.content.cart;

import com.xdsty.orderclient.re.CartAdditionalItemRe;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private String price;

    /**
     * 购物车商品所选择的附加项
     */
    private List<CartAdditionalItemContent> cartAdditionalItems;

    public static void main(String[] args) {
        List<CartAdditionalItemRe> cartAdditionalItemRes = new ArrayList<>();
        CartAdditionalItemRe re = new CartAdditionalItemRe();
        re.setCartId(1L); re.setAdditionalId(1L);
        cartAdditionalItemRes.add(re);

        re = new CartAdditionalItemRe();
        re.setCartId(1L); re.setAdditionalId(2L);
        cartAdditionalItemRes.add(re);

        re = new CartAdditionalItemRe();
        re.setCartId(2L); re.setAdditionalId(1L);
        cartAdditionalItemRes.add(re);

        re = new CartAdditionalItemRe();
        re.setCartId(1L); re.setAdditionalId(3L);
        cartAdditionalItemRes.add(re);

        Map<Long, List<CartAdditionalItemRe>> cartAddItemMap = cartAdditionalItemRes.stream()
                    .collect(Collectors.groupingBy(CartAdditionalItemRe::getCartId, Collectors.toList()));
        List<CartAdditionalItemRe> ee = cartAddItemMap.get(1L);
        ee.forEach(e -> System.out.println(e.getAdditionalId()));
    }

}
