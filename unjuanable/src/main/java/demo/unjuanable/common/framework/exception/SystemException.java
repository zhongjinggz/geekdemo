package demo.unjuanable.common.framework.exception;

public class SystemException extends RuntimeException {

    public SystemException(String msg) {
        super(msg);
    }
    public SystemException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
