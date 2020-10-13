package com.xdsty.txservice.service;

import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderProductAddDto;
import com.xdsty.orderclient.re.OrderAddRe;
import com.xdsty.orderclient.service.OrderTxService;
import com.xdsty.productclient.dto.ProductStorageDto;
import com.xdsty.productclient.dto.ProductStorageListDto;
import com.xdsty.productclient.service.StorageTxService;
import com.xdsty.txclient.service.OrderTransactionService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.stream.Collectors;

@DubboService(version = "1.0", timeout = 3000)
public class OrderTransactionServiceImpl implements OrderTransactionService {

    private OrderTxService orderTxService;

    private StorageTxService storageTxService;

    public OrderTransactionServiceImpl(OrderTxService orderTxService, StorageTxService storageTxService) {
        this.orderTxService = orderTxService;
        this.storageTxService = storageTxService;
    }

    @Override
    @GlobalTransactional
    public OrderAddRe placeOrder(OrderAddDto dto) {
        List<ProductStorageDto> storageDtos = dto.getProductDtos().stream().map(this::convert2ProductStorageDto).collect(Collectors.toList());
        ProductStorageListDto listDto = new ProductStorageListDto();
        listDto.setProductStorageDtos(storageDtos);
        storageTxService.prepare(null, listDto);
        return orderTxService.prepare(null, dto);
    }

    private ProductStorageDto convert2ProductStorageDto(OrderProductAddDto orderProductAddDto) {
        ProductStorageDto dto = new ProductStorageDto();
        dto.setProductId(orderProductAddDto.getProductId());
        dto.setProductNum(orderProductAddDto.getProductNum());
        return dto;
    }
}
