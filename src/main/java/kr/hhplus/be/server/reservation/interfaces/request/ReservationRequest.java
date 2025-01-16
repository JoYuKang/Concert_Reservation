package kr.hhplus.be.server.reservation.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationRequest {

    private Long memberId;

    private Long concertId;

    private List<Integer> seatNumbers;

}
