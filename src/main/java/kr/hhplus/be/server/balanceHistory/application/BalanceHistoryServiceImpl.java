package kr.hhplus.be.server.balanceHistory.application;

import kr.hhplus.be.server.balanceHistory.domain.BalanceHistory;
import kr.hhplus.be.server.balanceHistory.domain.BalanceHistoryService;
import kr.hhplus.be.server.balanceHistory.domain.BalanceStatus;
import kr.hhplus.be.server.balanceHistory.infrastructure.BalanceHistoryJpaRepository;
import kr.hhplus.be.server.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceHistoryServiceImpl implements BalanceHistoryService {

    private final BalanceHistoryJpaRepository balanceRepository;

    @Override
    public void insertHistory(Member member, Integer amount, BalanceStatus status) {
        balanceRepository.save(new BalanceHistory(member, amount, status));
    }

}
