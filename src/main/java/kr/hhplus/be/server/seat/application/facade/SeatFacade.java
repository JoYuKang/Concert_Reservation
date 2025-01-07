package kr.hhplus.be.server.seat.application.facade;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.domain.ConcertService;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatFacade {

    private final SeatService seatService;

    private final ConcertService concertService;

    public List<Seat> getConcernedSeats(Long concertId) {

        Concert concert = concertService.getById(concertId);

        return seatService.getSeats(concert);
    }


}
