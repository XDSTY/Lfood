package basecommon.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * 价格计算工具类
 */
public final class PriceCalculateUtil {

    /**
     * 判断两个数字四舍五入后是否相等
     * @param b1
     * @param b2
     * @return
     */
    public static boolean equals(BigDecimal b1, BigDecimal b2) {
        return b1.setScale(Constant.PRICE_SCALE, Constant.PRICE_CALCULATE_MODE)
                .equals(b2.setScale(Constant.PRICE_SCALE, Constant.PRICE_CALCULATE_MODE));
    }

    /**
     * 相加 保留2位小数，四舍五入
     * @param b1
     * @param b2
     * @return
     */
    public static <T extends Number> BigDecimal add(T b1, T b2) {
        BigDecimal v1 = checkNumber(b1), v2 = checkNumber(b2);
        return add(v1, v2, Constant.PRICE_SCALE, Constant.PRICE_CALCULATE_MODE);
    }

    private static <T extends Number> BigDecimal checkNumber(T num) {
        if(Objects.isNull(num)) {
            throw new RuntimeException("入参不能为空");
        }
        BigDecimal res = null;
        if(!(num instanceof BigDecimal)) {
            return new BigDecimal(num.toString());
        }
        return (BigDecimal) num;
    }

    /**
     * 两数相加
     * @param b1
     * @param b2
     * @param scale 小数位数
     * @param mode 小数截取规则
     * @return
     */
    public static <T extends BigDecimal> BigDecimal add(T b1, T b2, int scale, RoundingMode mode) {
        return b1.add(b2).setScale(scale, mode);
    }

    /**
     * 两数相乘  保留2位小数，四舍五入
     * @param b1
     * @param b2
     * @return
     */
    public static <T extends Number> BigDecimal multiply(T b1, T b2) {
        BigDecimal v1 = checkNumber(b1), v2 = checkNumber(b2);
        return v1.multiply(v2).setScale(Constant.PRICE_SCALE, Constant.PRICE_CALCULATE_MODE);
    }

}
