package kr.hhplus.be.server.reservation.infrastructure;

import kr.hhplus.be.server.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJPARepository extends JpaRepository<Reservation, Long> {
}
