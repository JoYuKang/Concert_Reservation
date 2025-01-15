package kr.hhplus.be.server.seat.domain;

import kr.hhplus.be.server.concert.domain.Concert;

import java.util.List;

public interface SeatService {

    List<Seat> getSeats(Concert concert);

    void saveSeatAll(List<Seat> seats);

    List<Seat> searchSeatWithLock(Long concertId, List<Integer> seatNumbers);
}
