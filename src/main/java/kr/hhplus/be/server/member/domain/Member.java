package kr.hhplus.be.server.member.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.support.common.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tb_member")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer balance;

    public void chargeBalance(int amount) {
        balance += amount;
    }

    public void reduceBalance(int amount) {
        balance -= amount;
        if (balance < 0) throw new IllegalArgumentException();
    }

}
