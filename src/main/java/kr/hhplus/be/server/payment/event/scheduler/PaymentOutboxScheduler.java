package kr.hhplus.be.server.payment.event.scheduler;

import kr.hhplus.be.server.payment.event.outbox.PaymentOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentOutboxScheduler {

    private final PaymentOutboxService paymentOutboxService;

    @Scheduled(fixedRate = 15000)
    public void retryFailedMessages() {
        log.info("Outbox 재처리 시작...");
        paymentOutboxService.publishPendingMessages();
    }
}
