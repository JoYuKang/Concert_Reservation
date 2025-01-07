package kr.hhplus.be.server.balanceHistory.infrastructure;

import kr.hhplus.be.server.balanceHistory.domain.BalanceHistory;
import kr.hhplus.be.server.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceHistoryJpaRepository extends JpaRepository<BalanceHistory, Long> {
    List<BalanceHistory> findByMemberId(Member memberId);
}
