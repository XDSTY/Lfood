package com.xdsty.orderclient.service;

import com.xdsty.orderclient.dto.CartItemDelDto;
import com.xdsty.orderclient.dto.CartItemDto;
import com.xdsty.orderclient.dto.CartItemListDto;
import com.xdsty.orderclient.dto.CartItemModifyDto;
import com.xdsty.orderclient.re.CartItemAddRe;
import com.xdsty.orderclient.re.CartItemDelRe;
import com.xdsty.orderclient.re.CartItemModifyRe;
import com.xdsty.orderclient.re.CartListRe;


/**
 * 购物车service
 */
public interface CartService {

    /**
     * 添加商品到购物车
     *
     * @param dto 商品项
     * @return
     */
    CartItemAddRe addCartItem(CartItemDto dto);

    /**
     * 查询购物车列表接口
     *
     * @param dto 入参
     * @return
     */
    CartListRe getCartItemList(CartItemListDto dto);

    /**
     * 增加购物车商品数量
     *
     * @param dto 购物车id
     * @return
     */
    CartItemModifyRe incrCartItem(CartItemModifyDto dto);

    /**
     * 减少购物车商品数量
     *
     * @param dto 购物车id
     * @return
     */
    CartItemModifyRe descCartItem(CartItemModifyDto dto);

    /**
     * 删除购物车商品
     *
     * @param dto 购物车id
     * @return
     */
    CartItemDelRe delCartItem(CartItemDelDto dto);

}
