package basecommon.enums;

/**
 * @author 张富华
 * @date 2020/9/11 11:07
 */
public enum PayChannelEnum {
    /**
     * 支付方式
     */
    ALI_PAY(1, "支付宝");

    private Integer channel;

    private String desc;

    PayChannelEnum(Integer channel, String desc) {
        this.channel = channel;
        this.desc = desc;
    }
}
