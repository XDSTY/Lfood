package com.xdsty.orderservice.mapper;

import com.xdsty.algorderclient.dto.CartItemListDto;
import com.xdsty.orderservice.entity.CartAdditional;
import com.xdsty.orderservice.entity.CartItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/9 16:57
 */
@Repository
public interface CartMapper {

    /**
     * 添加购物车条目
     * @param cartItem 购物车商品
     */
    void insertCartItem(CartItem cartItem);

    /**
     * 批量插入
     * @param additionals
     */
    void insertCartAdditional(@Param("additionals") List<CartAdditional> additionals);

    /**
     * 检查该商品是否已经在购物车
     * @param userId 用户id
     * @param productId 商品id
     * @return
     */
    List<Long> getCartIdByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 根据购物车id获取附加列表
     * @param cartId 购物车id
     * @return
     */
    List<Long> getAdditionalIdsByCartId(Long cartId);

    /**
     * 增加购物车商品数量
     * @param cartItem 购物车对象
     */
    void incrCartItemNum(CartItem cartItem);

    /**
     * 减少购物车商品数量
     * @param cartItem
     */
    Integer descCartItemNum(CartItem cartItem);

    /**
     * 判断购物车项是否存在 存在则返回商品数量
     * @param cartItem 购物车信息
     * @return
     */
    Integer checkUserCartExist(CartItem cartItem);

    /**
     * 删除购物车商品
     * @param cartId 购物车id
     */
    void delCartItem(Long cartId);

    /**
     * 获取购物车商品列表
     * @param dto 查询 分页信息
     * @return
     */
    List<CartItem> getCartItemList(CartItemListDto dto);

    /**
     * 根据购物车id查询附加id
     * @param cartIds 购物车id列表
     * @return
     */
    List<CartAdditional> getCartAdditionalByCartIds(@Param("cartIds") List<Long> cartIds);

    /**
     * 获取购物车商品的附加
     * @param cartId 购物车id
     * @return
     */
    List<CartAdditional> getCartItemAdditional(Long cartId);

}
