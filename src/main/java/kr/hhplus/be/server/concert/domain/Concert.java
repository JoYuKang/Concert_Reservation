package kr.hhplus.be.server.concert.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.support.common.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "tb_concert")
@AllArgsConstructor
@NoArgsConstructor
public class Concert extends Timestamped {

    @Id
    @Column(name = "concert_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate concertDate;

}
