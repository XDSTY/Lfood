package com.xdsty.productclient.service;


import com.xdsty.productclient.dto.ProductIdDto;
import com.xdsty.productclient.dto.ProductQueryDto;
import com.xdsty.productclient.dto.ProductValidDto;
import com.xdsty.productclient.re.*;

import java.util.List;

/**
 * @author 张富华
 * @date 2020/6/8 13:47
 */
public interface ProductService {

    /**
     * 根据id查询商品详情
     *
     * @param dto dto
     * @return 商品
     */
    ProductDetailRe getProductById(ProductIdDto dto);

    /**
     * 商品列表查询
     *
     * @param dto 查询条件
     * @return 商品列表
     */
    List<ProductListRe> getProductList(ProductQueryDto dto);

    /**
     * 根据商品id列表获取商品信息列表
     *
     * @param productIdList 商品id列表
     * @return 商品信息列表
     */
    List<CartItemProductRe> getCartItemProductList(List<Long> productIdList);

    /**
     * 获取购物车附加
     *
     * @param itemIds id列表
     * @return
     */
    List<AdditionalItemRe> getCartAdditionalItemList(List<Long> itemIds);

    /**
     * 检查商品是否正常
     *
     * @param dto
     * @return
     */
    ProductCheckRe checkProductValid(ProductValidDto dto);
}
