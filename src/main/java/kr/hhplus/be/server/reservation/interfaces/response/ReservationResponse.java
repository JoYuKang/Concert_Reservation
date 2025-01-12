package kr.hhplus.be.server.reservation.interfaces.response;

import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.seat.domain.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private String concertTitle;

    private LocalDate concertDate;

    private Integer totalAmount;

    private List<Integer> seatNumber;

    public ReservationResponse(Reservation reservation) {
        this.concertTitle = reservation.getConcert().getTitle();
        this.concertDate = reservation.getConcert().getConcertDate();
        this.totalAmount = reservation.getTotalAmount();
        this.seatNumber = reservation.getSeats().stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.toList());
    }

}
