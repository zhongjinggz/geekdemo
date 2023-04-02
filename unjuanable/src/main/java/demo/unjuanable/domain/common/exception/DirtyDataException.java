package demo.unjuanable.domain.common.exception;

public class DirtyDataException extends RuntimeException {
    public DirtyDataException(String msg) {
        super(msg);
    }
}
