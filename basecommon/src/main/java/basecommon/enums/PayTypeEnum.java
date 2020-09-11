package basecommon.enums;

/**
 * @author 张富华
 * @date 2020/9/11 11:05
 */
public enum PayTypeEnum {
    /**
     * 付款类型
     */
    ORDER_PAY(1, "下单");

    private Integer type;

    private String desc;

    PayTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
