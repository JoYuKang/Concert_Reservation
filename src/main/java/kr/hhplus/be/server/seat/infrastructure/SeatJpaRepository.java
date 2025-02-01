package kr.hhplus.be.server.seat.infrastructure;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByConcertAndStatus(Concert concert, SeatStatus status);
    
    @Query("SELECT s FROM Seat s WHERE s.concert.id = :concertId AND s.seatNumber IN :positions")
    List<Seat> findByConcertIdAndPosition(@Param("concertId") Long concertId, @Param("positions") List<Integer> positions);

}
