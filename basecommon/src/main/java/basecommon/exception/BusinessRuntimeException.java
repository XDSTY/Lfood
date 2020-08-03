package basecommon.exception;

/**
 * 公共业务异常
 *
 * @author 张富华
 * @date 2020/6/16 15:04
 */
public class BusinessRuntimeException extends RuntimeException {

    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(Throwable cause) {
        super(cause);
    }
}
