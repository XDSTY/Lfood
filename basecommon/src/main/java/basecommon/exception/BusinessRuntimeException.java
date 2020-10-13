package basecommon.exception;

/**
 * 公共业务异常
 *
 * @author 张富华
 * @date 2020/6/16 15:04
 */
public class BusinessRuntimeException extends RuntimeException {

    private Integer code;

    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(Throwable cause) {
        super(cause);
    }

    public BusinessRuntimeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
