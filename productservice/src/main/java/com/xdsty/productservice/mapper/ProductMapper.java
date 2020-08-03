package com.xdsty.productservice.mapper;


import com.xdsty.productclient.re.ProductListRe;
import com.xdsty.productservice.entity.AdditionalItem;
import com.xdsty.productservice.entity.Product;
import com.xdsty.productservice.entity.ProductListQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 张富华
 * @date 2020/6/2 14:32
 */
@Repository
public interface ProductMapper {

    /**
     * 根据id查询商品
     *
     * @param id 商品id
     * @return 商品信息
     */
    Product selectOne(Long id);

    /**
     * 查询商品的banner图
     *
     * @param productId 商品id
     * @return
     */
    List<String> selectProductBanner(Long productId);

    /**
     * 查询商品的详情图
     *
     * @param productId 商品id
     * @return
     */
    List<String> selectProductDetailImg(Long productId);

    /**
     * 查询商品的附加选项
     *
     * @param productId 商品id
     * @return
     */
    List<AdditionalItem> selectAdditionalItem(Long productId);

    /**
     * 查询商品列表
     *
     * @return
     */
    List<ProductListRe> selectProductList(ProductListQuery query);

    /**
     * 根据id集合查询商品列表
     *
     * @param productIds id集合
     * @return
     */
    List<Product> selectProductListByIds(@Param("productIds") List<Long> productIds);

    /**
     * 根据id集合查询附加值
     *
     * @param itemIds id列表
     * @return
     */
    List<AdditionalItem> selectAdditionalItemByIds(@Param("itemIds") List<Long> itemIds);

    /**
     * 检查商品是否正常 正常则返回商品id
     *
     * @param productId 商品Id
     * @return
     */
    Long selectValidProduct(Long productId);

    /**
     * 查询有效的附加数量
     *
     * @param itemIds 附加id集合
     * @return
     */
    Integer selectValidAdditionalCount(@Param("itemIds") List<Long> itemIds);

}
