package com.xdsty.orderservice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 张富华
 * @date 2020/9/4 10:22
 */
public final class AmountUtil {

    /**
     * 取两位小数，四舍五入  比较是否相同
     * @return
     */
    public static boolean equalAmount(BigDecimal p1, BigDecimal p2) {
        return p1.setScale(2, RoundingMode.HALF_UP).equals(p2.setScale(2, RoundingMode.HALF_UP));
    }

}
