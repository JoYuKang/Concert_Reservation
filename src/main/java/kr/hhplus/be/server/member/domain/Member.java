package kr.hhplus.be.server.member.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.support.common.Timestamped;
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

    public void chargeBalance(int amount) {
        if (amount < 0) throw new IllegalArgumentException();
        balance += amount;
    }

    public void reduceBalance(int amount) {
        if (amount < 0) throw new IllegalArgumentException("금액이 부족합니다.");
        balance -= amount;
        if (balance < 0) throw new IllegalArgumentException();
    }

}
