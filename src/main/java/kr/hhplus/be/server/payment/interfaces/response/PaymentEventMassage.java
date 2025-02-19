package kr.hhplus.be.server.payment.interfaces.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import kr.hhplus.be.server.payment.domain.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentEventMassage {

    private String eventMemberName;

    private String eventType;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventTime;

    public PaymentEventMassage(Payment payment) {
        this.eventMemberName = payment.getMember().getName();
        this.eventType = payment.getStatus().name();
        this.eventTime = payment.getUpdateTime();
    }

}
