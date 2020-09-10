package basecommon.util;

import java.math.RoundingMode;

public final class Constant {

    /**
     * 价格计算Bigdecimal默认模式
     */
    public static final RoundingMode PRICE_CALCULATE_MODE = RoundingMode.HALF_UP;

    /**
     * 价格保存两位小数
     */
    public static final int PRICE_SCALE = 2;

}
