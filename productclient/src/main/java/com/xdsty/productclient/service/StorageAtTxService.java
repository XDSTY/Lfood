package com.xdsty.productclient.service;

import com.xdsty.productclient.dto.ProductStorageListDto;

/**
 * @author 张富华
 * @date 2020/8/12 18:12
 */
public interface StorageAtTxService {

    /**
     * 扣除商品的库存
     * @param dto
     */
    void deduct(ProductStorageListDto dto);

}
