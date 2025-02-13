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
import kr.hhplus.be.server.payment.interfaces.PaymentEventListener;
import kr.hhplus.be.server.payment.interfaces.request.PaymentRequest;
import kr.hhplus.be.server.payment.interfaces.response.PaymentEventMassage;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationService;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.ExpiredException;
import kr.hhplus.be.server.support.exception.UnauthorizedReservationException;
import kr.hhplus.be.server.token.application.service.TokenRedisService;
import kr.hhplus.be.server.token.domain.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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

    private final TokenRedisService tokenRedisService;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Payment createPayment(Long memberId, PaymentRequest request, String token) {

        // 맴버 확인
        memberService.findById(memberId);

        // 예약 확인
        Reservation reservation = reservationService.getReservationById(request.getReservationId(), memberId);

        // 예약 유효성 확인
        reservationService.validate(reservation);

        // 맴버 잔액 차감
        Member reduceMember = memberService.reduceBalance(memberId, reservation.getTotalAmount());

        // 맴버 잔액 기록 저장
        balanceHistoryService.insertHistory(reduceMember, reservation.getTotalAmount(), BalanceStatus.USE);

        // 결제 내역 저장
        Payment payment = paymentService.create(new Payment(reservation.getMember(), reservation, reservation.getTotalAmount(), PaymentStatus.COMPLETED));

        // 예약 업데이트
        reservationService.confirmReservation(reservation.getId());

        // 토큰 만료
//        tokenService.expire(token);
        tokenRedisService.removeActiveToken(token);

        // 이벤트 발행 (결제가 성공적으로 완료된 후)
        eventPublisher.publishEvent(new PaymentEventMassage(payment));

        return payment;
    }

}
