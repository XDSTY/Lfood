package com.xdsty.api.controller.content;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/6/8 16:39
 */
@Setter
@Getter
@ApiModel
public class ProductContent {

    private Long productId;

    private String productName;

    private Boolean limitNum;

    private Integer totalNum;

    private Integer saledNum;

    private Integer remainingNum;

    private Date cutOffTime;

    private String price;

    private String thumbnail;

    private List<String> bannerList;

    private List<AdditionalItemContent> additionalItemRes;

    private List<String> images;
}