package entity.exception;

public class AssertException extends RuntimeException {
    public AssertException() {

    }

    public AssertException(Throwable e) {
        super(e);
    }

    public AssertException(String msg) {
        super(msg);
    }

    public AssertException(String msg, Throwable e) {
        super(msg, e);
    }
}
