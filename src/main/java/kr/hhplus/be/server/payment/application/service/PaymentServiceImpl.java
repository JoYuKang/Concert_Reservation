package kr.hhplus.be.server.payment.application.service;

import kr.hhplus.be.server.payment.domain.Payment;
import kr.hhplus.be.server.payment.domain.PaymentService;
import kr.hhplus.be.server.payment.infrastructure.PaymentJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment create(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

}
