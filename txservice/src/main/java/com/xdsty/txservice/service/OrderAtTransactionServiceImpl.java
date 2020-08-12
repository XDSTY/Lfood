package com.xdsty.txservice.service;

import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderProductAddDto;
import com.xdsty.orderclient.service.OrderAtTxService;
import com.xdsty.productclient.dto.ProductStorageDto;
import com.xdsty.productclient.dto.ProductStorageListDto;
import com.xdsty.productclient.service.StorageAtTxService;
import com.xdsty.txclient.service.OrderAtTransactionService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.stream.Collectors;

@DubboService(version = "1.0", timeout = 3000)
public class OrderAtTransactionServiceImpl implements OrderAtTransactionService {

    @DubboReference(version = "1.0")
    private StorageAtTxService storageAtTxService;

    @DubboReference(version = "1.0")
    private OrderAtTxService orderAtTxService;

    @Override
    @GlobalTransactional
    public long placeOrder(OrderAddDto dto) {
        // 先尝试减库存操作
        List<ProductStorageDto> storageDtos = dto.getProductDtos().stream().map(this::convert2ProductStorageDto).collect(Collectors.toList());
        ProductStorageListDto listDto = new ProductStorageListDto();
        listDto.setUserId(1L);
        listDto.setProductStorageDtos(storageDtos);

        storageAtTxService.deduct(listDto);
        return orderAtTxService.placeOrder(dto);
    }

    private ProductStorageDto convert2ProductStorageDto(OrderProductAddDto orderProductAddDto) {
        ProductStorageDto dto = new ProductStorageDto();
        dto.setProductId(orderProductAddDto.getProductId());
        dto.setProductNum(orderProductAddDto.getProductNum());
        return dto;
    }
}
