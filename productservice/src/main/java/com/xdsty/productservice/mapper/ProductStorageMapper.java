package com.xdsty.productservice.mapper;

import com.xdsty.productservice.entity.ProductModifyStorage;
import com.xdsty.productservice.entity.ProductStorage;
import org.springframework.stereotype.Repository;

/**
 * @author 张富华
 * @date 2020/7/29 14:32
 */
@Repository
public interface ProductStorageMapper {

    /**
     * 锁定商品库存
     * @param storage 需要锁定的库存
     * @return
     */
    int lockProductStorage(ProductStorage storage);

    /**
     * 解锁商品库存
     * @param storage 锁定的库存
     * @return
     */
    int unlockProductStorage(ProductStorage storage);

    /**
     * 提交冻结的库存，清空冻结的库存
     * @param storage 锁定的库存
     * @return
     */
    int commitProductStorage(ProductStorage storage);

    /**
     * 减去商品库存
     * @param storage 库存
     * @return
     */
    int deductProductStorage(ProductModifyStorage storage);

    /**
     * 订单失效回滚库存
     * @return
     */
    int incrProductStorage(ProductModifyStorage storage);

}
