package kr.hhplus.be.server.payment.interfaces.response;

import kr.hhplus.be.server.payment.domain.Payment;
import kr.hhplus.be.server.payment.domain.PaymentStatus;
import kr.hhplus.be.server.seat.domain.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String concertTitle;

    private Integer amount;

    private List<Integer> seatNumber;

    private PaymentStatus status;

    public PaymentResponse(Payment payment) {
        this.concertTitle = payment.getReservation().getConcert().getTitle();  // Concert 제목
        this.amount = payment.getAmount();  // Payment 금액
        this.seatNumber = payment.getReservation().getSeats().stream().map(Seat::getSeatNumber).collect(Collectors.toList());
        this.status = payment.getStatus();  // 결제 상태
    }

}
