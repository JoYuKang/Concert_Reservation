package kr.hhplus.be.server.payment.infrastructure;

import kr.hhplus.be.server.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
