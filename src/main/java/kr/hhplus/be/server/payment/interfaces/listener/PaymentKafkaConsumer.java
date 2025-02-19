package kr.hhplus.be.server.payment.interfaces.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.payment.interfaces.response.PaymentEventMassage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentKafkaConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "payment-events", groupId = "payment-group")
    public void consumePaymentEvent(String message) {
        try {
            PaymentEventMassage paymentEvent = objectMapper.readValue(message, PaymentEventMassage.class);
            log.info("Received Kafka Payment Event: {}", paymentEvent);
        } catch (Exception e) {
            log.error("Error processing payment event", e);
        }
    }
}
