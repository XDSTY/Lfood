package com.xdsty.orderservice.service;

import basecommon.exception.BusinessRuntimeException;
import com.xdsty.orderclient.dto.OrderAddDto;
import com.xdsty.orderclient.dto.OrderProductAddDto;
import com.xdsty.orderclient.service.OrderTxService;
import com.xdsty.orderservice.entity.Order;
import com.xdsty.orderservice.entity.OrderProduct;
import com.xdsty.orderservice.entity.enums.OrderStatus;
import com.xdsty.orderservice.mapper.OrderMapper;
import com.xdsty.orderservice.mapper.OrderProductMapper;
import com.xdsty.orderservice.mapper.TransactionMapper;
import com.xdsty.orderservice.transaction.Transaction;
import com.xdsty.orderservice.transaction.TransactionEnum;
import com.xdsty.orderservice.util.IdWorker;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
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

    public OrderTxServiceImpl(OrderMapper orderMapper, OrderProductMapper orderProductMapper, TransactionMapper transactionMapper) {
        this.orderMapper = orderMapper;
        this.orderProductMapper = orderProductMapper;
        this.transactionMapper = transactionMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long prepare(BusinessActionContext context, OrderAddDto dto) {
        log.error("context: {}", context);
        if (CollectionUtils.isEmpty(dto.getProductDtos())) {
            throw new BusinessRuntimeException("订单商品信息不能为空");
        }
        Transaction transactionRecord = transactionMapper.getTransaction(context.getXid(), context.getBranchId());
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
        Transaction transaction = new Transaction(context.getXid(), context.getBranchId(), TransactionEnum.INIT.status);
        if (transactionMapper.insertTransaction(transaction) <= 0) {
            throw new BusinessRuntimeException("新建事务记录失败，xid: " + context.getXid() + ", branchId:" + context.getBranchId());
        }
        context.getActionContext().put("orderId", orderId);
        return orderId;
    }

    private Order convert2Order(OrderAddDto dto, long orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(dto.getUserId());
        order.setTotalPrice(dto.getTotalPrice());
        return order;
    }

    private OrderProduct convert2OrderProduct(OrderProductAddDto dto, long orderId) {
        OrderProduct product = new OrderProduct();
        product.setOrderId(orderId);
        product.setProductId(dto.getProductId());
        product.setProductNum(dto.getProductNum());
        product.setProductPrice(dto.getProductPrice());
        return product;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext context) {
        log.error("order commit ..., context: {}", context);
        Long orderId = (Long) context.getActionContext("orderId");
        if (orderId == null) {
            log.error("orderId 为null");
            return true;
        }
        Transaction transactionRecord = transactionMapper.getTransaction(context.getXid(), context.getBranchId());
        if (transactionRecord == null || !TransactionEnum.INIT.status.equals(transactionRecord.getStatus())) {
            return true;
        }
        // 修改订单状态为未付款
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(OrderStatus.WAIT_PAY.status);
        if (orderMapper.updateOrder(order) <= 0) {
            throw new RuntimeException("修改订单失败, orderId: " + orderId);
        }
        Transaction transaction = new Transaction(context.getXid(), context.getBranchId(), TransactionEnum.COMMITTED.status);
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
        // TODO 验证是否可以获取orderId
        Long orderId = (Long) context.getActionContext("orderId");
        if (orderId == null) {
            log.error("orderId为null");
            return true;
        }
        // 防止空回滚
        Transaction transactionRecord = transactionMapper.getTransaction(context.getXid(), context.getBranchId());
        // 表示rollback先于try请求到达，发生了空回滚，则新建事务记录，可以防止后面到达的try阶段的请求
        if (transactionRecord == null) {
            Transaction transaction = new Transaction(context.getXid(), context.getBranchId(), TransactionEnum.INIT.status);
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
        order.setOrderId(orderId);
        order.setStatus(OrderStatus.PAY_FAIL.status);
        if (orderMapper.updateOrder(order) <= 0) {
            throw new RuntimeException("修改订单失败, orderId: " + orderId);
        }
        // 设置事务记录为已回滚
        Transaction transaction = new Transaction(context.getXid(), context.getBranchId(), TransactionEnum.ROLLBACK.status);
        if (transactionMapper.updateTransaction(transaction) <= 0) {
            throw new BusinessRuntimeException("回滚失败，orderId: " + orderId);
        }
        log.error("rollback success");
        return true;
    }
}
