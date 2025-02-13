package kr.hhplus.be.server.payment.interfaces;

import kr.hhplus.be.server.payment.application.service.PaymentNotificationService;
import kr.hhplus.be.server.payment.interfaces.response.PaymentEventMassage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventListener {

    private final PaymentNotificationService notificationService; // 알림 API 호출 서비스

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePaymentEvent(PaymentEventMassage message) {
        try {
            // 외부 알림 API 호출
            Boolean success = notificationService.sendPaymentNotification(message);
            if (success) log.info("알림 전송 성공: 결제 맴버 이름 - {}", message.getEventMemberName());
        } catch (Exception e) {
            // 알림 실패 시 로깅 처리 (결제 트랜잭션에는 영향 없음)
            log.error(e.getMessage(), e);
        }
    }
}
