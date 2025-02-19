package kr.hhplus.be.server.payment.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.payment.interfaces.response.PaymentEventMassage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "payment-events";
    private final ObjectMapper objectMapper;

    public void sendPaymentEvent(PaymentEventMassage message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message); // 객체 → JSON 변환
            kafkaTemplate.send(TOPIC, jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("직렬화 실패 ", e);
        }
    }
}
