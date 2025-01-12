package kr.hhplus.be.server.balanceHistory.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.support.common.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name ="tb_balance_history")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceHistory extends Timestamped {

    @Id
    @Column(name = "balance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Member memberId;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private BalanceStatus status; // 충전, 사용

    public BalanceHistory(Member member, Integer amount, BalanceStatus status) {
        this.memberId = member;
        this.amount = amount;
        this.status = status;
    }

}
