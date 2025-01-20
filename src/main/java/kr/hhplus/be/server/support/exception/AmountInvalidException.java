package kr.hhplus.be.server.support.exception;

public class AmountInvalidException extends RuntimeException {
    public AmountInvalidException(String message) {
        super(message);
    }
}
