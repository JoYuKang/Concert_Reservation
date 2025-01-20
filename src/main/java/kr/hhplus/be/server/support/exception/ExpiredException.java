package kr.hhplus.be.server.support.exception;

public class ExpiredException extends RuntimeException {
    public ExpiredException(String message) {
        super(message);
    }
}
