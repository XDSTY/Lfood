package com.xdsty.productservice.service;

import com.xdsty.productservice.entity.ProductModifyStorage;
import com.xdsty.productservice.mapper.ProductStorageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StorageService {

    @Resource
    private ProductStorageMapper productStorageMapper;

    /**
     * 更新商品的库存
     * @param storageList
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateProductStorage(List<ProductModifyStorage> storageList) {
        for(ProductModifyStorage storage : storageList) {
            productStorageMapper.incrProductStorage(storage);
        }
    }

}
