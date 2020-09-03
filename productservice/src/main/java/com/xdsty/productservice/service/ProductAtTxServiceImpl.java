package com.xdsty.productservice.service;

import basecommon.exception.BusinessRuntimeException;
import com.xdsty.productclient.dto.ProductStorageDto;
import com.xdsty.productclient.dto.ProductStorageListDto;
import com.xdsty.productclient.service.StorageAtTxService;
import com.xdsty.productservice.entity.ProductDeductStorage;
import com.xdsty.productservice.mapper.ProductStorageMapper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 张富华
 * @date 2020/8/12 18:15
 */
@Service
@DubboService(version = "1.0")
public class ProductAtTxServiceImpl implements StorageAtTxService {

    private static final Logger log = LoggerFactory.getLogger(ProductAtTxServiceImpl.class);

    private ProductStorageMapper productStorageMapper;

    public ProductAtTxServiceImpl(ProductStorageMapper productStorageMapper) {
        this.productStorageMapper = productStorageMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    public void deduct(ProductStorageListDto dto) {
        log.error("减库存: {}, 事务xid: {}", dto, RootContext.getXID());
        for(ProductStorageDto storage : dto.getProductStorageDtos()) {
            if(productStorageMapper.deductProductStorage(convert2ProductDeductStorage(storage)) <= 0) {
                log.error("减库存异常{}", dto);
                throw new BusinessRuntimeException("库存不足");
            }
        }
    }

    private ProductDeductStorage convert2ProductDeductStorage(ProductStorageDto dto) {
        ProductDeductStorage storage = new ProductDeductStorage();
        storage.setProductId(dto.getProductId());
        storage.setStorage(dto.getProductNum());
        return storage;
    }
}
