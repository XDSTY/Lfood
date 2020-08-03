package com.xdsty.api.controller;

import com.xdsty.api.config.annotation.PackageResult;
import com.xdsty.api.controller.content.AdditionalItemContent;
import com.xdsty.api.controller.content.ProductContent;
import com.xdsty.api.controller.content.ProductListContent;
import com.xdsty.api.controller.param.ProductIdParam;
import com.xdsty.api.controller.param.ProductListQueryParam;
import com.xdsty.api.util.PriceUtil;
import com.xdsty.productclient.dto.ProductIdDto;
import com.xdsty.productclient.dto.ProductQueryDto;
import com.xdsty.productclient.re.AdditionalItemRe;
import com.xdsty.productclient.re.ProductDetailRe;
import com.xdsty.productclient.re.ProductListRe;
import com.xdsty.productclient.service.ProductService;
import io.swagger.annotations.*;
import javax.validation.Valid;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张富华
 * @date 2020/6/8 16:09
 */
@Validated
@Api(value = "ProductController相关接口")
@PackageResult
@RestController
@RequestMapping("product")
public class ProductController {

    @DubboReference(version = "1.0")
    private ProductService productService;

    @ApiOperation(value = "根据商品id获取商品的详细信息", response = ProductContent.class)
    @PostMapping("getProductDetail/v1")
    public ProductContent getProductDetail(@RequestBody @Valid ProductIdParam param) {
        ProductIdDto dto = new ProductIdDto();
        dto.setId(param.getProductId());
        ProductDetailRe re = productService.getProductById(dto);
        return convert2ProductContent(re);
    }

    private ProductContent convert2ProductContent(ProductDetailRe re) {
        ProductContent content = new ProductContent();
        content.setProductId(re.getId());
        content.setCutOffTime(re.getCutOffTime());
        content.setLimitNum(re.getLimitNum());
        content.setPrice(PriceUtil.formatMoney(re.getPrice()));
        content.setProductName(re.getProductName());
        content.setSaledNum(re.getSaledNum());
        content.setRemainingNum(re.getRemainingNum());
        content.setTotalNum(re.getTotalNum());
        content.setThumbnail(re.getThumbnail());
        content.setBannerList(re.getBannerList());
        content.setImages(re.getImages());
        if (CollectionUtils.isNotEmpty(re.getAdditionalItemRes())) {
            content.setAdditionalItemRes(re.getAdditionalItemRes().stream().map(this::convert2AdditionalItemContent).collect(Collectors.toList()));
        }
        return content;
    }

    private AdditionalItemContent convert2AdditionalItemContent(AdditionalItemRe re) {
        return new AdditionalItemContent(re.getId(), re.getName(), PriceUtil.formatMoney(re.getPrice()));
    }

    @ApiOperation(value = "查询商品列表", response = ProductListContent.class)
    @PostMapping("getProductList/v1")
    public List<ProductListContent> getProductList(@RequestBody @Valid ProductListQueryParam param) {
        ProductQueryDto dto = new ProductQueryDto();
        dto.setCityId(param.getCityId());
        dto.setProductName(param.getProductName());
        dto.setPage(param.getPage());
        dto.setPageSize(param.getPageSize());
        List<ProductListRe> res = productService.getProductList(dto);
        return res.stream().map(this::convert2ProductListContent).collect(Collectors.toList());
    }

    private ProductListContent convert2ProductListContent(ProductListRe re) {
        ProductListContent content = new ProductListContent();
        content.setProductId(re.getProductId());
        content.setProductName(re.getProductName());
        content.setPrice(PriceUtil.formatMoney(re.getPrice()));
        content.setThumbnail(re.getThumbnail());
        return content;
    }
}