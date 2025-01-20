package kr.hhplus.be.server.reservation.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.support.common.Timestamped;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.PaymentStatusException;
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
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "concert_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Concert concert;

    @Column(name = "seat_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private List<Seat> seats = new ArrayList<>();

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // CONFIRMED, CANCELLED, AWAITING_PAYMENT

    public Reservation updateStatus(ReservationStatus status) {
        if (this.status != ReservationStatus.AWAITING_PAYMENT) throw new PaymentStatusException(ErrorMessages.ERROR_PAYMENT_NOT_ALLOWED);
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
        this.status = ReservationStatus.AWAITING_PAYMENT;
    }

}
