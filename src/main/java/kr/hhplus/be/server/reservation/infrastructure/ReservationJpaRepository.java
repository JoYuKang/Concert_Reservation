package kr.hhplus.be.server.reservation.infrastructure;

import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberId(Long member_id);

    @Query(value = "SELECT * FROM tb_reservation r WHERE r.status = 'AWAITING_PAYMENT' AND r.created_at < DATE_SUB(NOW(), INTERVAL 5 MINUTE)", nativeQuery = true)
    List<Reservation> findByOverTime();

}
