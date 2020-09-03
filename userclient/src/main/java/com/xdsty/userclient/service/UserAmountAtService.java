package com.xdsty.userclient.service;

import com.xdsty.userclient.dto.AmountDudctDto;

/**
 * @author 张富华
 * @date 2020/9/3 18:06
 */
public interface UserAmountAtService {

    /**
     * 扣款并添加流水
     */
    void dudctAmount(AmountDudctDto dto);

}
