package com.xdsty.api.util;

import java.math.BigDecimal;

/**
 * @author 张富华
 * @date 2020/7/1 15:09
 */
public class PriceUtil {

    /**
     * 金额格式化为保留2位小数
     *
     * @param price 金额
     * @return 格式化后的字符串
     */
    public static String formatMoney(BigDecimal price) {
        return price.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

}
