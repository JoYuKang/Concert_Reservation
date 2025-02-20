package kr.hhplus.be.server.payment.event.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kr.hhplus.be.server.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentOutboxService {

    private final PaymentOutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    // Payment 엔티티 받아서 OutboxMessage 생성 및 저장
    @Transactional
    public void saveOutboxMessage(Payment payment) {
        PaymentOutboxMessage outboxMessage = new PaymentOutboxMessage(payment);
        outboxRepository.save(outboxMessage);
    }

    // Kafka 메시지 발행 및 Outbox 상태 업데이트
    @Transactional
    public void publishPendingMessages() {
        List<PaymentOutboxMessage> pendingMessages = outboxRepository.findByStatus(PaymentMessageStatus.PENDING);
        for (PaymentOutboxMessage message : pendingMessages) {
            try {
                kafkaTemplate.send("payment-events", String.valueOf(message.getPaymentId()), message.getPayload());
                message.updateStatus(PaymentMessageStatus.SUCCESS);
                outboxRepository.save(message);
                log.info("Kafka 발행 성공: {}", message.getPaymentId());
            } catch (Exception e) {
                message.updateStatus(PaymentMessageStatus.FAILED);
                outboxRepository.save(message);
                log.error( "Kafka 발행 실패 (재시도 대상): {}", message.getPaymentId(), e);
            }
        }
    }

}
