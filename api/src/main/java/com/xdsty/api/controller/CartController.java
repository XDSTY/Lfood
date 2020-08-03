package com.xdsty.api.controller;

import basecommon.util.PageUtil;
import com.google.common.collect.Lists;
import com.xdsty.api.config.annotation.PackageResult;
import com.xdsty.api.controller.content.*;
import com.xdsty.api.controller.param.*;
import com.xdsty.api.util.PriceUtil;
import com.xdsty.api.util.SessionUtil;
import com.xdsty.orderclient.dto.CartAdditionalItem;
import com.xdsty.orderclient.dto.CartItemDelDto;
import com.xdsty.orderclient.dto.CartItemDto;
import com.xdsty.orderclient.dto.CartItemListDto;
import com.xdsty.orderclient.dto.CartItemModifyDto;
import com.xdsty.orderclient.re.CartAdditionalItemRe;
import com.xdsty.orderclient.re.CartItemRe;
import com.xdsty.orderclient.re.CartListRe;
import com.xdsty.orderclient.service.CartService;
import com.xdsty.productclient.dto.ProductValidDto;
import com.xdsty.productclient.re.AdditionalItemRe;
import com.xdsty.productclient.re.CartItemProductRe;
import com.xdsty.productclient.service.ProductService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 张富华
 * @date 2020/7/15 14:49
 */
@RequestMapping("cart")
@PackageResult
@RestController
public class CartController {

    @DubboReference(version = "1.0")
    private CartService cartService;

    @DubboReference(version = "1.0")
    private ProductService productService;

    /**
     * 获取购物车列表
     *
     * @param param 分页参数
     * @return
     */
    @RequestMapping("list/v1")
    public List<CartItemContent> getCartItemList(@RequestBody CartListParam param) {
        CartItemListDto listDto = new CartItemListDto();
        listDto.setUserId(SessionUtil.getUserId());
        PageUtil.initPageInfo(listDto, param);
        // 获取购物车列表
        CartListRe cartListRe = cartService.getCartItemList(listDto);
        List<CartItemRe> cartItemRes = cartListRe.getCartItemRes();
        if (!CollectionUtils.isEmpty(cartItemRes)) {
            List<Long> productIds = cartItemRes.stream().map(CartItemRe::getProductId).collect(Collectors.toList());
            // 获取购物车里商品列表
            List<CartItemProductRe> productRes = productService.getCartItemProductList(productIds);
            // 商品列表为空 则表示购物车里面商品已被删除或下架
            if (CollectionUtils.isEmpty(productRes)) {
                return Lists.newArrayList();
            }

            // 购物车的附加为空
            if (CollectionUtils.isEmpty(cartListRe.getCartAdditionalItemRes())) {
                return cartItemRes.stream()
                        .map(e -> convert2CartItemContent(e, getProductRe(productRes, e.getProductId())))
                        .filter(Objects::isNull).collect(Collectors.toList());
            }
            // 获取购物车商品的附加
            List<CartAdditionalItemRe> cartAdditionalItemRes = cartListRe.getCartAdditionalItemRes();
            List<Long> additionalIds = cartAdditionalItemRes.stream().map(CartAdditionalItemRe::getAdditionalId).collect(Collectors.toList());
            List<AdditionalItemRe> additionalItemRes = productService.getCartAdditionalItemList(additionalIds);
            // 调整为 cartId - list<AdditionalId>
            Map<Long, List<Long>> cartIdMap = cartAdditionalItemRes.stream()
                    .collect(Collectors.groupingBy(CartAdditionalItemRe::getCartId, Collectors.mapping(CartAdditionalItemRe::getAdditionalId, Collectors.toList())));
            return cartItemRes.stream().map(e -> convert2CartItemContent(e, getProductRe(productRes, e.getProductId()), cartIdMap, additionalItemRes)).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    private CartItemContent convert2CartItemContent(CartItemRe cartItemRe, CartItemProductRe productRe,
                                                    Map<Long, List<Long>> cartIdMap, List<AdditionalItemRe> additialItemRes) {
        CartItemContent content = convert2CartItemContent(cartItemRe, productRe);
        List<Long> additionalIds = cartIdMap.get(cartItemRe.getCartId());
        if (content == null || CollectionUtils.isEmpty(additionalIds)) {
            return content;
        }
        BigDecimal price = productRe.getProductPrice();
        StringBuilder productName = new StringBuilder(content.getProductName());
        List<AdditionalItemRe> validAdditionalRes = additionalIds.stream().map(e -> getAdditionalItemRe(additialItemRes, e)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(validAdditionalRes)) {
            productName.append("(");
            for (AdditionalItemRe re : validAdditionalRes) {
                price = price.add(re.getPrice());
                productName.append(re.getName()).append("、");
            }
        }
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(cartItemRe.getProductNum()));
        productName.setCharAt(productName.length() - 1, ')');
        content.setTotalPrice(PriceUtil.formatMoney(totalPrice));
        content.setPrice(PriceUtil.formatMoney(price));
        content.setProductName(productName.toString());
        return content;
    }

    private AdditionalItemRe getAdditionalItemRe(List<AdditionalItemRe> additionalItemRes, Long id) {
        return additionalItemRes.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    private CartItemProductRe getProductRe(List<CartItemProductRe> productRes, Long productId) {
        return productRes.stream().filter(e -> e.getProductId().equals(productId)).findFirst().orElse(null);
    }

    private CartItemContent convert2CartItemContent(CartItemRe cartItemRe, CartItemProductRe productRe) {
        if (productRe == null) {
            return null;
        }
        CartItemContent content = new CartItemContent();
        content.setCartId(cartItemRe.getCartId());
        content.setProductId(cartItemRe.getProductId());
        content.setNum(cartItemRe.getProductNum());
        content.setProductName(productRe.getProductName());
        content.setThumbnail(productRe.getThumbnail());
        content.setTotalPrice(PriceUtil.formatMoney(productRe.getProductPrice().multiply(BigDecimal.valueOf(cartItemRe.getProductNum()))));
        return content;
    }

    /**
     * 添加购物车接口
     *
     * @param param
     * @return
     */
    @PostMapping("add/v1")
    public void addCartItem(@RequestBody CartItemAddParam param) {
        Long userId = SessionUtil.getUserId();
        List<Long> additionalIds = null;
        if (!CollectionUtils.isEmpty(param.getCartItemAddParams())) {
            additionalIds = param.getCartItemAddParams().stream().map(CartAdditionalParam::getAdditionalId).collect(Collectors.toList());
        }
        // 判断商品和商品的附加项是否处于正常状态  未下架和删除
        checkValidProduct(param.getProductId(), additionalIds);
        // 添加购物车
        CartItemDto dto = new CartItemDto();
        dto.setUserId(userId);
        dto.setProductId(param.getProductId());
        dto.setProductNum(param.getProductNum());
        if (!CollectionUtils.isEmpty(param.getCartItemAddParams())) {
            List<CartAdditionalItem> cartAdditionalItemList = param.getCartItemAddParams().stream().map(e -> {
                CartAdditionalItem item = new CartAdditionalItem();
                item.setId(e.getAdditionalId());
                item.setNum(e.getNum());
                return item;
            }).collect(Collectors.toList());
            dto.setAdditionalList(cartAdditionalItemList);
        }
        // 添加购物车
        cartService.addCartItem(dto);
    }

    /**
     * 增加购物车数量
     *
     * @param param
     * @return
     */
    @PostMapping("incr/v1")
    public void incrCartItemNum(@RequestBody CartItemIncrParam param) {
        Long userId = SessionUtil.getUserId();
        // 判断商品是否处于正常状态  未下架和删除
        checkValidProduct(param.getProductId(), null);
        CartItemModifyDto dto = new CartItemModifyDto();
        dto.setCartId(param.getCartId());
        dto.setModifyNum(param.getNum());
        dto.setProductId(param.getProductId());
        dto.setUserId(userId);
        cartService.incrCartItem(dto);
    }

    @PostMapping("desc/v1")
    public void descCartItemNum(@RequestBody CartItemDescParam param) {
        Long userId = SessionUtil.getUserId();
        // 判断商品是否处于正常状态  未下架和删除
        checkValidProduct(param.getProductId(), null);
        CartItemModifyDto dto = new CartItemModifyDto();
        dto.setCartId(param.getCartId());
        dto.setModifyNum(param.getNum());
        dto.setProductId(param.getProductId());
        dto.setUserId(userId);
        cartService.descCartItem(dto);
    }

    private void checkValidProduct(Long productId, List<Long> additionalIds) {
        ProductValidDto dto = new ProductValidDto();
        dto.setProductId(productId);
        dto.setProductAdditionalIds(additionalIds);
        productService.checkProductValid(dto);
    }

    /**
     * 删除购物车
     *
     * @param param
     * @return
     */
    @PostMapping("del/v1")
    public void delCartItem(@RequestBody CartItemDelParam param) {
        Long userId = SessionUtil.getUserId();
        CartItemDelDto dto = new CartItemDelDto();
        dto.setUserId(userId);
        dto.setProductId(param.getProductId());
        dto.setCartId(param.getCartId());
        cartService.delCartItem(dto);
    }

}
