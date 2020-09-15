package com.xdsty.orderservice.service;

import basecommon.exception.BusinessRuntimeException;
import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderProductAddDto;
import com.xdsty.orderclient.dto.OrderProductAdditionalDto;
import com.xdsty.orderclient.enums.OrderStatusEnum;
import com.xdsty.orderclient.service.OrderTxService;
import com.xdsty.orderservice.common.Constant;
import com.xdsty.orderservice.entity.Order;
import com.xdsty.orderservice.entity.OrderAdditional;
import com.xdsty.orderservice.entity.OrderProduct;
import com.xdsty.orderservice.mapper.OrderAdditionalMapper;
import com.xdsty.orderservice.mapper.OrderMapper;
import com.xdsty.orderservice.mapper.OrderProductMapper;
import com.xdsty.orderservice.mapper.TransactionMapper;
import com.xdsty.orderservice.nacos.ConfigCenter;
import com.xdsty.orderservice.nacos.ConfigKeyEnum;
import com.xdsty.orderservice.transaction.OrderTransaction;
import com.xdsty.orderservice.transaction.TransactionEnum;
import com.xdsty.orderservice.util.IdWorker;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 张富华
 * @date 2020/7/28 10:38
 */
@Service
@DubboService(version = "1.0")
public class OrderTxServiceImpl implements OrderTxService {

    private static final Logger log = LoggerFactory.getLogger(OrderTxServiceImpl.class);

    private OrderMapper orderMapper;

    private OrderProductMapper orderProductMapper;

    private TransactionMapper transactionMapper;

    private OrderAdditionalMapper orderAdditionalMapper;

    public OrderTxServiceImpl(OrderMapper orderMapper, OrderProductMapper orderProductMapper, TransactionMapper transactionMapper, OrderAdditionalMapper orderAdditionalMapper) {
        this.orderMapper = orderMapper;
        this.orderProductMapper = orderProductMapper;
        this.transactionMapper = transactionMapper;
        this.orderAdditionalMapper = orderAdditionalMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long prepare(BusinessActionContext context, OrderAddDto dto) {
        log.error("context: {}", context);
        if (CollectionUtils.isEmpty(dto.getProductDtos())) {
            throw new BusinessRuntimeException("订单商品信息不能为空");
        }
        OrderTransaction transactionRecord = transactionMapper.getTransaction(context.getXid(), context.getBranchId());
        // 该事务已存在  表明该请求是重复请求
        if (transactionRecord != null) {
            return -1L;
        }
        // 雪花算法生成分布式唯一id
        long orderId = IdWorker.getNextId();
        //新建订单和订单商品
        Order order = convert2Order(dto, orderId);
        int count = orderMapper.insertOrder(order);
        if (count <= 0) {
            throw new BusinessRuntimeException("新建订单失败，xid: " + context.getXid() + ", branchId:" + context.getBranchId());
        }
        List<OrderProduct> products = dto.getProductDtos().stream().map(e -> convert2OrderProduct(e, orderId)).collect(Collectors.toList());
        count = orderProductMapper.addOrderProduct(products);
        if (count != products.size()) {
            throw new BusinessRuntimeException("新建订单商品信息失败");
        }
        // 订单附加项插入
        List<OrderAdditional> orderAdditionals = initOrderAdditionalList(products);
        if(!CollectionUtils.isEmpty(orderAdditionals)) {
            count = orderAdditionalMapper.insertOrderProductAdditional(orderAdditionals);
            if(count != orderAdditionals.size()) {
                throw new BusinessRuntimeException("新建订单失败");
            }
        }

        OrderTransaction transaction = new OrderTransaction(context.getXid(), context.getBranchId(), TransactionEnum.INIT.status, orderId);
        if (transactionMapper.insertTransaction(transaction) <= 0) {
            throw new BusinessRuntimeException("新建事务记录失败，xid: " + context.getXid() + ", branchId:" + context.getBranchId());
        }
        return orderId;
    }

    /**
     * 获取订单商品对应的附加项列表
     * @param products
     * @return
     */
    private List<OrderAdditional> initOrderAdditionalList(List<OrderProduct> products) {
        List<OrderProduct> availProducts = products.stream().filter(e -> !CollectionUtils.isEmpty(e.getOrderAdditionals())).collect(Collectors.toList());
        int totalSize = availProducts.stream().map(e -> e.getOrderAdditionals().size()).reduce(Integer::sum).orElse(0);
        if(totalSize == 0) {
            return null;
        }
        List<OrderAdditional> orderAdditionals = new ArrayList<>(totalSize);
        for(OrderProduct product : availProducts) {
            for(OrderAdditional additional : product.getOrderAdditionals()) {
                additional.setOrderProductId(product.getId());
            }
            orderAdditionals.addAll(product.getOrderAdditionals());
        }
        return orderAdditionals;
    }

    private Order convert2Order(OrderAddDto dto, long orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(dto.getUserId());
        order.setTotalPrice(dto.getTotalPrice());
        // 设置订单的下单时间和支付结束时间
        Date now = new Date();
        order.setCreateTime(now);
        order.setPayEndTime(new Date(now.getTime() + Constant.DEFAULT_ORDER_WAIT_TIME));
        //从配置中心获取待支付订单维持时间
        String configTme = ConfigCenter.getConfigValue(ConfigKeyEnum.ORDER_WAIT_PAY_TIME.dataId);
        if(Objects.nonNull(configTme)) {
            order.setPayEndTime(new Date(now.getTime() + Long.valueOf(configTme)));
        }
        return order;
    }

    private OrderProduct convert2OrderProduct(OrderProductAddDto dto, Long orderId) {
        OrderProduct product = new OrderProduct();
        product.setOrderId(orderId);
        product.setProductId(dto.getProductId());
        product.setProductNum(dto.getProductNum());
        product.setProductPrice(dto.getProductPrice());
        if(!CollectionUtils.isEmpty(dto.getOrderProductAdditionals())) {
            List<OrderAdditional> orderAdditionals = dto.getOrderProductAdditionals().stream().map(e -> convert2OrderAdditional(e, orderId)).collect(Collectors.toList());
            product.setOrderAdditionals(orderAdditionals);
        }
        return product;
    }

    private OrderAdditional convert2OrderAdditional(OrderProductAdditionalDto dto, Long orderId) {
        OrderAdditional additional = new OrderAdditional();
        additional.setAdditionalId(dto.getAdditionalId());
        additional.setNum(dto.getNum());
        additional.setPrice(dto.getPrice());
        additional.setOrderId(orderId);
        return additional;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext context) {
        log.error("order commit ..., context: {}", context);
        OrderTransaction transactionRecord = transactionMapper.getTransaction(context.getXid(), context.getBranchId());
        if (transactionRecord == null || !TransactionEnum.INIT.status.equals(transactionRecord.getStatus())) {
            return true;
        }
        Long orderId = transactionRecord.getOrderId();
        // 修改订单状态为未付款
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(OrderStatusEnum.WAIT_PAY.getStatus());
        if (orderMapper.updateOrder(order) <= 0) {
            throw new RuntimeException("修改订单失败, orderId: " + orderId);
        }
        OrderTransaction transaction = new OrderTransaction(context.getXid(), context.getBranchId(), TransactionEnum.COMMITTED.status);
        if (transactionMapper.updateTransaction(transaction) <= 0) {
            throw new BusinessRuntimeException("提交失败，orderId: " + orderId);
        }
        log.error("order commit success");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollback(BusinessActionContext context) {
        log.error("rollback start, context: {}", context);
        // 防止空回滚
        OrderTransaction transactionRecord = transactionMapper.getTransaction(context.getXid(), context.getBranchId());
        // 表示rollback先于try请求到达，发生了空回滚，则新建事务记录，可以防止后面到达的try阶段的请求
        if (transactionRecord == null) {
            OrderTransaction transaction = new OrderTransaction(context.getXid(), context.getBranchId(), TransactionEnum.ROLLBACK.status, -1L);
            int count = transactionMapper.insertTransaction(transaction);
            if (count <= 0) {
                throw new BusinessRuntimeException("事务记录为空，插入回滚记录失败，xid:" + context.getXid() + ", branchId: " + context.getBranchId());
            }
            return true;
        }
        // 如果事务记录不是初始化状态，则该事务已经回滚，则直接返回
        if (!TransactionEnum.INIT.status.equals(transactionRecord.getStatus())) {
            log.error("事务记录不为初始化状态，不进行操作，xid: {}, branchId: {}", context.getXid(), context.getBranchId());
            return true;
        }

        // 设置订单状态为已失效
        Order order = new Order();
        order.setOrderId(transactionRecord.getOrderId());
        order.setStatus(OrderStatusEnum.PAY_FAIL.getStatus());
        if (orderMapper.updateOrder(order) <= 0) {
            throw new RuntimeException("修改订单失败, orderId: " + transactionRecord.getOrderId());
        }
        // 设置事务记录为已回滚
        OrderTransaction transaction = new OrderTransaction(context.getXid(), context.getBranchId(), TransactionEnum.ROLLBACK.status);
        if (transactionMapper.updateTransaction(transaction) <= 0) {
            throw new BusinessRuntimeException("回滚失败，orderId: " + transactionRecord.getOrderId());
        }
        log.error("rollback success");
        return true;
    }
}
