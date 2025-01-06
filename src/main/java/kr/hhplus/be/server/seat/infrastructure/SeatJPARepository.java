package kr.hhplus.be.server.seat.infrastructure;

import kr.hhplus.be.server.seat.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatJPARepository extends JpaRepository<Seat, Long> {

    List<Seat> findByConcertId(Long concertId);
}
