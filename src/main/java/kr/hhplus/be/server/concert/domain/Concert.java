package kr.hhplus.be.server.concert.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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

    @JsonSerialize(using = LocalDateSerializer.class)  // LocalDate에 맞는 Serializer 사용
    @JsonDeserialize(using = LocalDateDeserializer.class)  // LocalDate에 맞는 Deserializer 사용
    private LocalDate concertDate;

    private String category;

}
