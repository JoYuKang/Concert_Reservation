package kr.hhplus.be.server.seat.application.service;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatService;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import kr.hhplus.be.server.seat.infrastructure.SeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatJpaRepository seatJpaRepository;


    @Override
    public List<Seat> getSeats(Concert concert) {
        return seatJpaRepository.findByConcertIdAndStatus(concert, SeatStatus.판매중);
    }

    @Override
    public List<Seat> saveSeatAll(List<Seat> seats) {
        return seatJpaRepository.saveAll(seats);
    }

}
