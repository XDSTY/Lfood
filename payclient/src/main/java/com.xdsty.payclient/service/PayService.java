package com.xdsty.payclient.service;

import com.xdsty.payclient.dto.UserAmountDto;
import com.xdsty.payclient.re.UserAmountRe;

/**
 * @author 张富华
 * @date 2020/9/15 18:07
 */
public interface PayService {

    /**
     * 获取用户的余额
     * @param userAmountDto
     * @return
     */
    UserAmountRe getUserAmount(UserAmountDto userAmountDto);

}
