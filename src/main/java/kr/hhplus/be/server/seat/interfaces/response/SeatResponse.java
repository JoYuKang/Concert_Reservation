package kr.hhplus.be.server.seat.interfaces.response;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {

    private String concertName;

    private Integer seatNumber;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    public SeatResponse(Seat seat) {
        this.concertName = seat.getConcert().getTitle();
        this.seatNumber = seat.getSeatNumber();
        this.amount = seat.getAmount();
        this.status = seat.getStatus();
    }
}
