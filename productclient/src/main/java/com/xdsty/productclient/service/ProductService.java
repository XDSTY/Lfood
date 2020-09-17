package com.xdsty.productclient.service;


import com.xdsty.productclient.dto.AdditionalListDto;
import com.xdsty.productclient.dto.OrderProductDto;
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
     * 根据商品id列表获取商品信息列表 只返回正常状态的附加项
     *
     * @param productIdList 商品id列表
     * @return 商品信息列表
     */
    List<CartItemProductRe> getCartItemProductList(List<Long> productIdList);

    /**
     * 根据附加项id获取附加项 只返回正常状态的附加项
     *
     * @param dto id列表
     * @return
     */
    List<AdditionalItemRe> getAdditionalItemList(AdditionalListDto dto);

    /**
     * 检查商品是否正常
     *
     * @param dto
     * @return
     */
    void checkProductValid(ProductValidDto dto);

    /**
     * 检验商品的价格和传入的是否相同
     * @param orderProductDtos
     * @return
     */
    void checkOrderProduct(List<OrderProductDto> orderProductDtos);

    /**
     * 获取订单商品信息，下架的商品的信息也会获取到
     * @param productIds
     * @return
     */
    List<OrderProductRe> getOrderProducts(List<Long> productIds);


}
