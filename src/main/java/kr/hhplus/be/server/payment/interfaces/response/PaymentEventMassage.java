package kr.hhplus.be.server.payment.interfaces.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.payment.domain.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentEventMassage {

    private String eventMemberName;

    private String eventType;

    private Integer amount;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventTime;

    public PaymentEventMassage(Member member, Payment payment) {
        this.eventMemberName = member.getName();
        this.eventType = payment.getStatus().name();
        this.eventTime = payment.getCreateTime();
        this.amount = payment.getAmount();
    }

}
