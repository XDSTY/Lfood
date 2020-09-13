package com.xdsty.orderservice.service;

import basecommon.exception.BusinessRuntimeException;
import com.xdsty.orderclient.dto.*;
import com.xdsty.orderclient.re.*;
import com.xdsty.orderclient.service.CartService;
import com.xdsty.orderservice.entity.CartAdditional;
import com.xdsty.orderservice.entity.CartItem;
import com.xdsty.orderservice.mapper.CartMapper;
import com.xdsty.orderservice.util.ListUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@DubboService(timeout = 3000, version = "1.0")
public class CartServiceImpl implements CartService {

    private CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartItemAddRe addCartItem(CartItemDto cartItemDto) {
        List<Long> cartIds = cartMapper.getCartIdByUserIdAndProductId(cartItemDto.getUserId(), cartItemDto.getProductId());
        if (!CollectionUtils.isEmpty(cartIds)) {
            for (Long cartId : cartIds) {
                List<Long> additionalItemIds = cartMapper.getAdditionalIdsByCartId(cartId);
                // 该商品已在购物车中
                if(CollectionUtils.isEmpty(additionalItemIds) && CollectionUtils.isEmpty(cartItemDto.getAdditionalList())) {
                    CartItem cartItem = getCartItem(cartId, cartItemDto);
                    cartMapper.incrCartItemNum(cartItem);
                    return new CartItemAddRe();
                }
                List<Long> additionalItemIdsDto = null;
                if (!CollectionUtils.isEmpty(cartItemDto.getAdditionalList())) {
                    additionalItemIdsDto = cartItemDto.getAdditionalList().stream().map(CartAdditionalItem::getId).collect(Collectors.toList());
                }
                // 新加入的商品已经在用户的购物车中
                if (ListUtil.equals(additionalItemIds, additionalItemIdsDto)) {
                    CartItem cartItem = getCartItem(cartId, cartItemDto);
                    cartMapper.incrCartItemNum(cartItem);
                    return new CartItemAddRe();
                }
            }
        }
        // 新建购物车中的商品
        CartItem cartItem = new CartItem();
        cartItem.setUserId(cartItemDto.getUserId());
        cartItem.setProductExtendId(cartItemDto.getProductId());
        cartItem.setProductNum(cartItemDto.getProductNum());
        cartMapper.insertCartItem(cartItem);
        // 新建购物车的附加
        if (!CollectionUtils.isEmpty(cartItemDto.getAdditionalList())) {
            List<CartAdditional> additionals = cartItemDto.getAdditionalList().stream().map(a -> convert2CartAdditional(a, cartItem.getId())).collect(Collectors.toList());
            cartMapper.insertCartAdditional(additionals);
        }
        return new CartItemAddRe();
    }

    private CartItem getCartItem(Long cartId, CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartId);
        cartItem.setProductExtendId(cartItemDto.getProductId());
        cartItem.setProductNum(cartItemDto.getProductNum());
        cartItem.setUserId(cartItemDto.getUserId());
        return cartItem;
    }

    private CartAdditional convert2CartAdditional(CartAdditionalItem additionalItem, Long cartId) {
        CartAdditional re = new CartAdditional();
        re.setCartId(cartId);
        re.setAdditionalId(additionalItem.getId());
        re.setNum(additionalItem.getNum());
        return re;
    }

    @Override
    public CartListRe getCartItemList(CartItemListDto dto) {
        CartListRe re = new CartListRe();
        // 获取购物车信息
        List<CartItem> cartItems = cartMapper.getCartItemList(dto);
        if (!CollectionUtils.isEmpty(cartItems)) {
            List<CartItemRe> cartItemRes = cartItems.stream().map(this::convert2CartItemRe).collect(Collectors.toList());
            re.setCartItemRes(cartItemRes);

            // 根据购物车信息获取商品附加信息
            List<Long> cartIds = cartItems.stream().map(CartItem::getId).collect(Collectors.toList());
            List<CartAdditional> cartAdditionals = cartMapper.getCartAdditionalByCartIds(cartIds);
            if (!CollectionUtils.isEmpty(cartAdditionals)) {
                List<CartAdditionalItemRe> cartAdditionalItemRes = cartAdditionals.stream().map(this::convert2CartAdditionalItemRe).collect(Collectors.toList());
                re.setCartAdditionalItemRes(cartAdditionalItemRes);
            }
        }
        return re;
    }

    private CartAdditionalItemRe convert2CartAdditionalItemRe(CartAdditional cartAdditional) {
        CartAdditionalItemRe itemRe = new CartAdditionalItemRe();
        itemRe.setCartId(cartAdditional.getCartId());
        itemRe.setAdditionalId(cartAdditional.getAdditionalId());
        itemRe.setNum(cartAdditional.getNum());
        return itemRe;
    }

    private CartItemRe convert2CartItemRe(CartItem cartItem) {
        CartItemRe re = new CartItemRe();
        re.setCartId(cartItem.getId());
        re.setProductId(cartItem.getProductExtendId());
        re.setProductNum(cartItem.getProductNum());
        return re;
    }

    @Override
    public CartItemModifyRe incrCartItem(CartItemModifyDto dto) {
        checkCartItemExistAndGetNum(dto.getCartId(), dto.getUserId(), dto.getProductId());
        CartItem cartItem = new CartItem(dto.getCartId(), dto.getProductId(), dto.getUserId(), dto.getModifyNum());
        cartMapper.incrCartItemNum(cartItem);
        return new CartItemModifyRe();
    }

    @Override
    public CartItemModifyRe descCartItem(CartItemModifyDto dto) {
        Integer num = checkCartItemExistAndGetNum(dto.getCartId(), dto.getUserId(), dto.getProductId());
        if (num <= dto.getModifyNum()) {
            throw new BusinessRuntimeException("购物车商品数量不足");
        }
        CartItem cartItem = new CartItem(dto.getCartId(), dto.getProductId(), dto.getUserId(), dto.getModifyNum());
        Integer count = cartMapper.descCartItemNum(cartItem);
        if (count != 1) {
            throw new BusinessRuntimeException("购物车商品数量不足");
        }
        return new CartItemModifyRe();
    }

    private Integer checkCartItemExistAndGetNum(Long cartId, Long userId, Long productId) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartId);
        cartItem.setUserId(userId);
        cartItem.setProductExtendId(productId);
        // 判断是否存在且未删除
        Integer num = cartMapper.checkUserCartExist(cartItem);
        if (num == null) {
            throw new BusinessRuntimeException("该购物车商品不存在");
        }
        return num;
    }

    @Override
    public CartItemDelRe delCartItem(CartItemDelDto dto) {
        checkCartItemExistAndGetNum(dto.getCartId(), dto.getUserId(), dto.getProductId());
        cartMapper.delCartItem(dto.getCartId());
        return new CartItemDelRe();
    }
}
