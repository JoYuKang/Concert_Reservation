package kr.hhplus.be.server.payment.application.facade;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.balanceHistory.domain.BalanceHistory;
import kr.hhplus.be.server.balanceHistory.domain.BalanceHistoryService;
import kr.hhplus.be.server.balanceHistory.domain.BalanceStatus;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.payment.domain.Payment;
import kr.hhplus.be.server.payment.domain.PaymentService;
import kr.hhplus.be.server.payment.domain.PaymentStatus;
import kr.hhplus.be.server.payment.interfaces.request.PaymentRequest;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationService;
import kr.hhplus.be.server.token.domain.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final ReservationService reservationService;

    private final PaymentService paymentService;

    private final MemberService memberService;

    private final BalanceHistoryService balanceHistoryService;

    private final TokenService tokenService;

    @Transactional
    public Payment createPayment(Long memberId, PaymentRequest request, String token) {

        // 예약 확인
        Reservation reservation = reservationService.getReservationById(request.getReservationId());

        // 요청 맴버와 예약의 맴버 일치 확인
        Member member = memberService.findById(memberId);
        if (!Objects.equals(member.getId(), reservation.getMember().getId())) {
            throw new RuntimeException("예약자와 다른 유저입니다.");
        }

        // 예약 유효성 확인
        if (reservation.getCreateTime().isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new RuntimeException("유효한 시간이 아닌 예약입니다.");
        }

        // 맴버 잔액 차감
        Member reduceMember = memberService.reduceBalance(memberId, reservation.getTotalAmount());

        // 맴버 잔액 기록 저장
        balanceHistoryService.insertHistory(reduceMember, reservation.getTotalAmount(), BalanceStatus.사용);

        // 결제 내역 저장
        Payment payment = paymentService.create(new Payment(reservation.getMember(), reservation, reservation.getTotalAmount(), PaymentStatus.완료));

        // 예약 업데이트
        reservationService.confirmReservation(reservation.getId());

        // 토큰 만료
        tokenService.expire(token);

        return payment;
    }

}
