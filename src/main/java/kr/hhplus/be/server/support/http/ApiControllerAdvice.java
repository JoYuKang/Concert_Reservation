package kr.hhplus.be.server.support.http;

import kr.hhplus.be.server.concert.interfaces.ConcertController;
import kr.hhplus.be.server.member.interfaces.MemberController;
import kr.hhplus.be.server.payment.interfaces.PaymentController;
import kr.hhplus.be.server.reservation.interfaces.ReservationController;
import kr.hhplus.be.server.seat.interfaces.SeatController;
import kr.hhplus.be.server.support.exception.*;
import kr.hhplus.be.server.token.interfaces.TokenController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AmountInvalidException.class)
    public ResponseEntity<ErrorResponse> amountInvalidException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("400", e.getMessage()));
    }

    @ExceptionHandler(value = InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> insufficientBalanceException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("400", e.getMessage()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("400", e.getMessage()));
    }

    @ExceptionHandler(value = SeatInvalidException.class)
    public ResponseEntity<ErrorResponse> seatInvalidException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("400", e.getMessage()));
    }

    @ExceptionHandler(value = InvalidIdException.class)
    public ResponseEntity<ErrorResponse> invalidIdException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("400", e.getMessage()));
    }

    @ExceptionHandler(value = UnauthorizedReservationException.class)
    public ResponseEntity<ErrorResponse> unauthorizedReservationException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("400", e.getMessage()));
    }

    @ExceptionHandler(value = ExpiredException.class)
    public ResponseEntity<ErrorResponse> expiredException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("400", e.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse("500", "에러가 발생했습니다."));
    }
}
