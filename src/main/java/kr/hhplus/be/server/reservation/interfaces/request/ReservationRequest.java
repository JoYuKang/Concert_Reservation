package kr.hhplus.be.server.reservation.interfaces.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ReservationRequest {

    private Long memberId;

    private Long concertId;

    private List<Integer> seatNumbers;

}
