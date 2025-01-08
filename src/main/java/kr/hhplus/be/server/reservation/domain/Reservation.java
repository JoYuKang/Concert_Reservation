package kr.hhplus.be.server.reservation.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.support.common.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Table(name = "tb_reservation")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "cpncert_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Concert concert;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", nullable = false)
    private List<Seat> seats = new ArrayList<>();

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation updateStatus(ReservationStatus status) {
        if (this.status != ReservationStatus.결제대기) throw new IllegalArgumentException("결제할 수 있는 상태가 아닙니다.");
        this.status = status;
        return this;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }

    public Reservation(Member member, Concert concert, List<Seat> seats) {
        this.member = member;
        this.concert = concert;
        this.seats = seats;
        this.totalAmount = seats.stream().mapToInt(Seat::getAmount).sum();
        this.status = ReservationStatus.결제대기;
    }

}
