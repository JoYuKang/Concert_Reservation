package kr.hhplus.be.server.payment.event.outbox;

public enum PaymentMessageStatus {
    PENDING,    // Kafka 발행 대기 중
    SUCCESS,    // Kafka 발행 성공
    FAILED      // Kafka 발행 실패
}
