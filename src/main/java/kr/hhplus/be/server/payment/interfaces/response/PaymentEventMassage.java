package kr.hhplus.be.server.payment.interfaces.response;

import kr.hhplus.be.server.payment.domain.Payment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentEventMassage {

    private String eventMemberName;

    private String eventType;

    private LocalDateTime eventTime;

    public PaymentEventMassage(Payment payment) {
        this.eventMemberName = payment.getMember().getName();
        this.eventType = payment.getStatus().name();
        this.eventTime = payment.getUpdateTime();
    }

}
