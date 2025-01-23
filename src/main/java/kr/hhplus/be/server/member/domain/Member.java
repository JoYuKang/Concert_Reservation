package kr.hhplus.be.server.member.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.support.common.Timestamped;
import kr.hhplus.be.server.support.exception.AmountInvalidException;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Table(name = "tb_member")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer balance;

    @Version
    @Column(nullable = false)
    private Integer version = 0; // JPA가 자동으로 관리할 필드

    public void chargeBalance(int amount) {
        if (amount < 0) throw new AmountInvalidException(ErrorMessages.NEGATIVE_BALANCE_NOT_ALLOWED);
        balance += amount;
    }

    public void reduceBalance(int amount) {
        if (amount < 0) throw new AmountInvalidException(ErrorMessages.INSUFFICIENT_BALANCE);
        balance -= amount;
        if (balance < 0) throw new AmountInvalidException(ErrorMessages.NEGATIVE_BALANCE_NOT_ALLOWED);
    }

    public Member(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

}
