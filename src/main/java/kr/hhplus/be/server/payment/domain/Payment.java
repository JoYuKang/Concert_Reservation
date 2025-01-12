package kr.hhplus.be.server.payment.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.support.common.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tb_payment")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends Timestamped {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "reservation_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // 완료, 취소

    public Payment(Member member, Reservation reservation, Integer amount, PaymentStatus status) {
        this.member = member;
        this.reservation = reservation;
        this.amount = amount;
        this.status = status;
    }

}
