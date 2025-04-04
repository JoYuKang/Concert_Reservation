package kr.hhplus.be.server.member.application.service;

import kr.hhplus.be.server.balanceHistory.application.BalanceHistoryServiceImpl;
import kr.hhplus.be.server.balanceHistory.domain.BalanceHistory;
import kr.hhplus.be.server.balanceHistory.domain.BalanceStatus;
import kr.hhplus.be.server.balanceHistory.infrastructure.BalanceHistoryJpaRepository;
import kr.hhplus.be.server.member.application.facade.MemberFacade;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.member.infrastructure.MemberJpaRepository;
import kr.hhplus.be.server.member.interfaces.dto.request.MemberCreateRequest;
import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;
import kr.hhplus.be.server.support.exception.AmountInvalidException;
import kr.hhplus.be.server.support.exception.DuplicateException;
import kr.hhplus.be.server.support.exception.NotFoundException;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("유저를 ID로 조회한다.")
    void findById() {
        // given
        Member member = new Member(1L, "test member" , 10000, 0);
        // when
        when(memberJpaRepository.findById(1L)).thenReturn(Optional.of(member));
        // then
        assertThat(memberService.findById(1L)).isEqualTo(member);
    }

    @Test
    @DisplayName("해당 ID의 유저를 찾을 수 없으면 조회에 실패한다.")
    void failedFindById(){
        // when, then
        assertThatThrownBy(() -> memberService.findById(1L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("유저의 잔고를 충전한다.")
    void chargeBalance() {
        // given
        Member member = new Member(1L, "test member" , 10000, 0);
        MemberRequest request = new MemberRequest(member.getId(), 5000);

        // when
        when(memberJpaRepository.findMemberWithLock(1L)).thenReturn(Optional.of(member));
        when(memberJpaRepository.save(any(Member.class))).thenReturn(member);

        // then
        assertThat(memberService.chargeBalance(request).getBalance()).isEqualTo(15000);
    }

    @Test
    @DisplayName("유저의 잔고 충전 시 유저를 찾을 수 없으면 충전에 실패한다.")
    void failedChargeBalance() {
        // given
        MemberRequest request = new MemberRequest(1L, 5000);
        // then
        assertThatThrownBy(() -> memberService.chargeBalance(request)).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("유저의 잔고 충전 시 유저를 찾을 수 없으면 충전에 실패한다.")
    void failedChargeMinusBalance() {
        // given
        Member member = new Member(1L, "test member" , 10000, 0);
        MemberRequest request = new MemberRequest(1L, -5000);
        when(memberJpaRepository.findMemberWithLock(1L)).thenReturn(Optional.of(member));

        // then
        assertThatThrownBy(() -> memberService.chargeBalance(request)).isInstanceOf(AmountInvalidException.class);
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void createMember() {
        // given
        Member member = new Member(1L, "test members" , 0, 0);
        MemberCreateRequest request = new MemberCreateRequest("test members");
        when(memberJpaRepository.findByName("test members")).thenReturn(List.of());
        when(memberJpaRepository.save(any(Member.class))).thenReturn(member);

        // then
        assertThat(memberService.createMember(request)).isEqualTo(request.getName());
    }

    @Test
    @DisplayName("이름이 중복되면 유저 생성에 실패한다.")
    void failedCreateMember() {
        // given
        Member member = new Member(1L, "test member" , 10000, 0);
        MemberCreateRequest request = new MemberCreateRequest("test member");
        when(memberJpaRepository.findByName("test member")).thenReturn(List.of(member));

        // then
        assertThatThrownBy(() -> memberService.createMember(request)).isInstanceOf(DuplicateException.class);
    }
}
