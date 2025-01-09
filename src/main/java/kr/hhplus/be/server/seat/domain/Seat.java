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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    @Column(name = "position")
    private Integer seatNumber;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;
}
