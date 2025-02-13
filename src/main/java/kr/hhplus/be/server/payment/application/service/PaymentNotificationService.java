package kr.hhplus.be.server.payment.application.service;

import kr.hhplus.be.server.payment.interfaces.response.PaymentEventMassage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentNotificationService {
    public Boolean sendPaymentNotification(PaymentEventMassage paymentEventMassage) {
        // 외부 API 호출 로직 (예: REST API 호출)
        log.info("결제 알림 전송: 결제 유저명 - {}", paymentEventMassage.getEventMemberName());
        return true;
    }
}
