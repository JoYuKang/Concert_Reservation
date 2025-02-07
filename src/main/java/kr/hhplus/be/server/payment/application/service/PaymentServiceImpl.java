package kr.hhplus.be.server.payment.application.service;

import kr.hhplus.be.server.payment.domain.Payment;
import kr.hhplus.be.server.payment.domain.PaymentService;
import kr.hhplus.be.server.payment.infrastructure.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment create(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

}
