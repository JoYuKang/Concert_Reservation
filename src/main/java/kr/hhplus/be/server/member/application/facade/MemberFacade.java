package kr.hhplus.be.server.member.application.facade;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.balanceHistory.domain.BalanceHistoryService;
import kr.hhplus.be.server.balanceHistory.domain.BalanceStatus;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;
import kr.hhplus.be.server.support.infra.lock.DistributedLock;
import kr.hhplus.be.server.support.infra.lock.type.LockType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;

    private final BalanceHistoryService balanceHistoryService;

    @DistributedLock(lockType = LockType.MEMBER, key = "#request.id")
    @Transactional
    public Integer chargeBalanceWithHistory(MemberRequest request) {
        // Member의 포인트 충전
        Member member = memberService.chargeBalance(request);

        // 충전 내역 기록
        balanceHistoryService.insertHistory(member, request.getAmount(), BalanceStatus.CHARGE);

        return member.getBalance();
    }

}
