package kr.hhplus.be.server.support.exception;

public class UnauthorizedReservationException extends RuntimeException {
    public UnauthorizedReservationException(String message) {
        super(message);
    }
}
