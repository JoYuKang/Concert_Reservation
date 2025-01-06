package kr.hhplus.be.server.concert.interfaces.dto.response;

import kr.hhplus.be.server.concert.domain.Concert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertResponse {

    String title;

    LocalDate concertTime;

    public ConcertResponse(Concert concert) {
        this.title =concert.getTitle();
        this.concertTime = concert.getConcertDate();
    }

}
