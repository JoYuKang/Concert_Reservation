package kr.hhplus.be.server.seat.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.concert.domain.Concert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tb_seat")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    @Column(name = "position")
    private Integer seatNumber;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private SeatStatus status; // 판매중, 매진

    public void reserve() {
        this.status = SeatStatus.매진;
    }

    public void cancle() {
        this.status = SeatStatus.판매중;
    }

}
