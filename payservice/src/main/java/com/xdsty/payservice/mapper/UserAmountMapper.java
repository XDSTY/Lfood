package com.xdsty.payservice.mapper;

import com.xdsty.payservice.entity.UserAmount;
import org.springframework.stereotype.Repository;

/**
 * @author 张富华
 */
@Repository
public interface UserAmountMapper {

    /**
     * 判断用户金额是否足够
     * @param amount
     * @return
     */
    Integer judgeAmount(UserAmount amount);

    /**
     * 减去用户金额
     * @param amount
     * @return
     */
    int deductAmount(UserAmount amount);

    /**
     * 获取用户的余额
     * @param userId
     * @return
     */
    UserAmount getUserAmount(long userId);

}
