package kr.hhplus.be.server.seat.infrastructure;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByConcertIdAndStatus(Concert concert, SeatStatus status);

}
