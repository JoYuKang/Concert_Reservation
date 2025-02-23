package kr.hhplus.be.server.payment.event.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.payment.domain.Payment;
import kr.hhplus.be.server.payment.interfaces.response.PaymentEventMassage;
import kr.hhplus.be.server.support.common.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_payment_messagee")
public class PaymentOutboxMessage extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType; // 이벤트 유형 (ex: "PAYMENT_COMPLETED")

    private Long memberId;

    private Long paymentId;

    @Lob
    private String payload; // JSON 직렬화된 데이터

    @Enumerated(EnumType.STRING)
    private PaymentMessageStatus status; // 메시지 상태 (PENDING, PROCESSED, FAILED)
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Payment 객체를 받아 JSON 변환
    public PaymentOutboxMessage(Member member, Payment payment) {
        this.eventType = "PAYMENT_COMPLETED"; // 기본 이벤트 타입
        this.memberId = member.getId();
        this.paymentId = payment.getId();
        this.status = PaymentMessageStatus.PENDING;

        PaymentEventMassage eventMassage = new PaymentEventMassage(member, payment);

        try {
            this.payload = objectMapper.writeValueAsString(eventMassage); // JSON 변환
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Payment eventMassage 객체를 JSON으로 변환하는 중 오류 발생", e);
        }
    }

    public void updateStatus(PaymentMessageStatus newStatus) {
        this.status = newStatus;
    }

}
