package com.xdsty.api.service;

import com.xdsty.api.config.nacos.ConfigCenter;
import com.xdsty.api.config.nacos.ConfigKeyEnum;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 积分计算
 * @author 张富华
 * @date 2020/9/4 10:43
 */
@Service
public class IntegralCalculateService {

    private static final BigDecimal DEFAULT_AMOUNT_TO_INTEGRAL = new BigDecimal(100);

    /**
     * 根据金额计算积分
     * @param amount
     * @return
     */
    public int calculateIntegral(BigDecimal amount) {
        String amountToIntegral = ConfigCenter.getConfigValue(ConfigKeyEnum.INTEGRAL_CONFIG.dataId);
        BigDecimal aToIntegral = DEFAULT_AMOUNT_TO_INTEGRAL;
        if(amountToIntegral != null) {
            aToIntegral = new BigDecimal(amountToIntegral);
        }
        if(amount.compareTo(aToIntegral) < 0) {
            return 0;
        }
        String is = amount.divide(aToIntegral, RoundingMode.DOWN).setScale(0, RoundingMode.DOWN).toString();
        return Integer.valueOf(is);
    }

}
