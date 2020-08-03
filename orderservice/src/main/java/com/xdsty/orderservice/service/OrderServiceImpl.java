package com.xdsty.orderservice.service;

import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderProductAddDto;
import com.xdsty.orderclient.service.OrderService;
import com.xdsty.orderclient.service.OrderTxService;
import com.xdsty.productclient.dto.ProductStorageDto;
import com.xdsty.productclient.dto.ProductStorageListDto;
import com.xdsty.productclient.service.StorageTxService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张富华
 * @date 2020/7/30 9:27
 */
@Service
@DubboService(version = "1.0", timeout = 3000)
public class OrderServiceImpl implements OrderService {

    private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderTxService orderTxService;

    private StorageTxService storageTxService;

    @Autowired
    public void setOrderTxService(OrderTxService orderTxService) {
        this.orderTxService = orderTxService;
    }

    @Autowired
    public void setStorageTxService(StorageTxService storageTxService) {
        this.storageTxService = storageTxService;
    }

    @Override
    @GlobalTransactional
    public void placeOrder(OrderAddDto orderAddDto) {
        List<ProductStorageDto> storageDtos = orderAddDto.getProductDtos().stream().map(this::convert2ProductStorageDto).collect(Collectors.toList());
        ProductStorageListDto dto = new ProductStorageListDto();
        dto.setUserId(1L);
        dto.setProductStorageDtos(storageDtos);
        storageTxService.prepare(null, dto);

        long orderId = orderTxService.prepare(null, orderAddDto);
    }

    private ProductStorageDto convert2ProductStorageDto(OrderProductAddDto orderProductAddDto) {
        ProductStorageDto dto = new ProductStorageDto();
        dto.setProductId(orderProductAddDto.getProductId());
        dto.setProductNum(orderProductAddDto.getProductNum());
        return dto;
    }
}
