package kr.hhplus.be.server.member.application.facade;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import kr.hhplus.be.server.balanceHistory.domain.BalanceHistoryService;
import kr.hhplus.be.server.balanceHistory.domain.BalanceStatus;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;

    private final BalanceHistoryService balanceHistoryService;

    @Retryable(
            retryFor = OptimisticLockException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 300)
    )
    @Transactional
    public Integer chargeBalanceWithHistory(MemberRequest request) {
        // Member의 포인트 충전
        Member member = memberService.chargeBalance(request);

        // 충전 내역 기록
        balanceHistoryService.insertHistory(member, request.getAmount(), BalanceStatus.CHARGE);

        return member.getBalance();
    }

}
