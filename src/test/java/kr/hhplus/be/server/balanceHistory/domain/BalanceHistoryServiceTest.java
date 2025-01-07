package kr.hhplus.be.server.balanceHistory.domain;

import kr.hhplus.be.server.balanceHistory.application.BalanceHistoryServiceImpl;
import kr.hhplus.be.server.balanceHistory.infrastructure.BalanceJpaRepository;
import kr.hhplus.be.server.member.application.service.MemberServiceImpl;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.member.infrastructure.MemberJpaRepository;
import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceHistoryServiceTest {

    @InjectMocks
    private BalanceHistoryServiceImpl balanceHistoryService;

    @Mock
    private BalanceJpaRepository balanceJpaRepository;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("유저의 잔고를 충전 시 History에 저장된다.")
    void chargeBalanceSetHistory() {
        // given
        Member member = new Member(1L, "test member" , 10000);
        MemberRequest request = new MemberRequest(member.getId(), 5000);

        // when
        when(memberJpaRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberJpaRepository.save(any(Member.class))).thenReturn(member);
        memberService.chargeBalance(request);
        balanceHistoryService.insertHistory(1L,5000,BalanceStatus.충전);

        // then
        assertThat(memberJpaRepository.findAll().size()).isEqualTo(1);
    }
}
