package kr.hhplus.be.server.reservation.infrastructure;

import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberId(Long member_id);
}
