package com.xdsty.productservice.service;

import basecommon.exception.BusinessRuntimeException;
import com.alibaba.fastjson.JSONObject;
import com.xdsty.productclient.dto.ProductStorageDto;
import com.xdsty.productclient.dto.ProductStorageListDto;
import com.xdsty.productclient.service.StorageTxService;
import com.xdsty.productservice.entity.ProductStorage;
import com.xdsty.productservice.mapper.ProductStorageMapper;
import com.xdsty.productservice.mapper.TransactionMapper;
import com.xdsty.productservice.transaction.Transaction;
import com.xdsty.productservice.transaction.TransactionEnum;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/29 11:27
 */
@Service
@DubboService(timeout = 3000, version = "1.0")
public class StorageTxServiceImpl implements StorageTxService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private TransactionMapper transactionMapper;

    private ProductStorageMapper productStorageMapper;

    public StorageTxServiceImpl(TransactionMapper transactionMapper, ProductStorageMapper productStorageMapper) {
        this.transactionMapper = transactionMapper;
        this.productStorageMapper = productStorageMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean prepare(BusinessActionContext context, ProductStorageListDto dto) {
        log.error("prepare ...., context: {}", context);
        if (CollectionUtils.isEmpty(dto.getProductStorageDtos())) {
            return false;
        }
        // 幂等
        Transaction transactionRecord = transactionMapper.getTransaction(context.getXid(), context.getBranchId());
        // 已存在当前事务记录，则该请求是重复的请求或者是 commit或rollback已执行，prepare才执行  不处理该请求
        if (transactionRecord != null) {
            return true;
        }
        // 新建事务记录
        Transaction transaction = new Transaction(context.getXid(), context.getBranchId(), TransactionEnum.INIT.status);
        transactionMapper.insertTransaction(transaction);

        // 锁定商品的库存
        for (ProductStorageDto storageDto : dto.getProductStorageDtos()) {
            ProductStorage productStorage = new ProductStorage(storageDto.getProductId(), storageDto.getProductNum());
            if (productStorageMapper.lockProductStorage(productStorage) <= 0) {
                log.error("锁定库存失败, xid: {}, branchId: {}", context.getXid(), context.getBranchId());
                throw new BusinessRuntimeException("库存不足");
            }
        }
        log.error("prepare success");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(BusinessActionContext context) {
        log.error("commit ... context:{}", context);
        Transaction transactionRecord = transactionMapper.getTransaction(context.getXid(), context.getBranchId());
        if (transactionRecord == null) {
            log.error("无法找到对应的事务记录, xid: {}, branchId: {}", context.getXid(), context.getBranchId());
            return true;
        }
        // 如果当前事务记录状态不为初始化状态，则该commit已经执行过
        if (!TransactionEnum.INIT.status.equals(transactionRecord.getStatus())) {
            return true;
        }
        // 提交操作
        JSONObject dtoJsonObj = (JSONObject) context.getActionContext("dto");
        List<ProductStorageDto> productStorageDtos = dtoJsonObj.getJSONArray("productStorageDtos").toJavaList(ProductStorageDto.class);
        for (ProductStorageDto storageDto : productStorageDtos) {
            ProductStorage productStorage = new ProductStorage(storageDto.getProductId(), storageDto.getProductNum());
            if (productStorageMapper.commitProductStorage(productStorage) <= 0) {
                throw new BusinessRuntimeException("提交库存操作失败，xid: " + context.getXid() + ", branchId: " + context.getBranchId());
            }
        }
        // 设置事务记录状态为已提交
        Transaction transaction = new Transaction(context.getXid(), context.getBranchId(), TransactionEnum.COMMITTED.status);
        if (transactionMapper.updateTransaction(transaction) <= 0) {
            throw new BusinessRuntimeException("修改事务记录为提交失败，xid: " + context.getXid() + ", branchId: " + context.getBranchId());
        }
        log.error("commit success");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollback(BusinessActionContext context) {
        log.error("rollback ... context:{} ", context);
        Transaction transactionRecord = transactionMapper.getTransaction(context.getXid(), context.getBranchId());
        // 当前rollback早于prepare阶段执行  则插入已回滚状态的事务记录  prepare在执行时查询到已存在该记录，则拒绝执行
        if (transactionRecord == null) {
            Transaction transaction = new Transaction(context.getXid(), context.getBranchId(), TransactionEnum.ROLLBACK.status);
            int count = transactionMapper.insertTransaction(transaction);
            if (count <= 0) {
                throw new BusinessRuntimeException("事务记录为空，插入回滚记录失败，xid:" + context.getXid() + ", branchId: " + context.getBranchId());
            }
            return true;
        }
        // 如果事务记录不是初始化状态，则直接返回
        if (!TransactionEnum.INIT.status.equals(transactionRecord.getStatus())) {
            log.error("事务记录不为初始化状态，不进行操作，xid: {}, branchId: {}", context.getXid(), context.getBranchId());
            return true;
        }

        // 归还冻结的资源
        JSONObject dtoJsonObj = (JSONObject) context.getActionContext("dto");
        List<ProductStorageDto> productStorageDtos = dtoJsonObj.getJSONArray("productStorageDtos").toJavaList(ProductStorageDto.class);
        for (ProductStorageDto storageDto : productStorageDtos) {
            ProductStorage productStorage = new ProductStorage(storageDto.getProductId(), storageDto.getProductNum());
            if (productStorageMapper.unlockProductStorage(productStorage) <= 0) {
                log.error("释放锁定库存失败, xid: {}, branchId: {}", context.getXid(), context.getBranchId());
                throw new BusinessRuntimeException("释放锁定库存失败");
            }
        }
        // 修改事务记录为回滚
        Transaction transaction = new Transaction(context.getXid(), context.getBranchId(), TransactionEnum.ROLLBACK.status);
        int count = transactionMapper.updateTransaction(transaction);
        if (count <= 0) {
            throw new BusinessRuntimeException("修改事务记录为回滚失败，xid: " + context.getXid() + ", branchId: " + context.getBranchId());
        }
        log.error("rollback success");
        return true;
    }
}
