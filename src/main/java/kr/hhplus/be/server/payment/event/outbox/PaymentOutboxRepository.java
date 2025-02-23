package kr.hhplus.be.server.payment.event.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentOutboxRepository extends JpaRepository<PaymentOutboxMessage, Long> {
    List<PaymentOutboxMessage> findByStatus(PaymentMessageStatus status);
}
