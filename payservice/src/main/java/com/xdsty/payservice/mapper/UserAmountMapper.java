package com.xdsty.payservice.mapper;

import com.xdsty.payservice.entity.UserAmount;
import org.springframework.stereotype.Repository;

/**
 * @author 张富华
 */
@Repository
public interface UserAmountMapper {

    /**
     * 减去用户金额
     * @param amount
     * @return
     */
    int deductAmount(UserAmount amount);

}
