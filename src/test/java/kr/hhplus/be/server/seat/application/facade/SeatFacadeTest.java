package kr.hhplus.be.server.seat.application.facade;

import kr.hhplus.be.server.concert.application.service.ConcertServiceImpl;
import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.infrastructure.ConcertJpaRepository;
import kr.hhplus.be.server.seat.application.service.SeatServiceImpl;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import kr.hhplus.be.server.seat.infrastructure.SeatJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatFacadeTest {

    @InjectMocks
    private SeatFacade seatFacade;

    @Mock
    private SeatServiceImpl seatService;

    @Mock
    private ConcertServiceImpl concertService;

    @Test
    @DisplayName("콘서트의 좌석을 조회할 수 있다.")
    void getConcernedSeats() {
        // given
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now(), "POP");
        Seat seat = new Seat(1L, concert, 14, 50000, SeatStatus.AVAILABLE, 0);

        // when
        when(concertService.getById(1L)).thenReturn(concert);
        when(seatService.getSeats(concert)).thenReturn(List.of(seat));

        // then
        assertThat(seatFacade.getConcernedSeats(1L)).isEqualTo(List.of(seat));
    }

    @Test
    @DisplayName("잘못된 콘서트 ID를 입력 시 조회에 실패한다.")
    void failedInvalidConcernedSeats() {

        // when
        when(concertService.getById(-1L)).thenThrow(new IllegalArgumentException("잘못된 Concert ID 값 입니다."));

        // then
        assertThatThrownBy(() -> seatFacade.getConcernedSeats(-1L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("콘서트가 존재하지 않을 시 조회에 실패한다.")
    void failedNotFoundConcernedSeats() {

        // when
        when(concertService.getById(1L)).thenThrow(new IllegalArgumentException("해당 콘서트가 존재하지 않습니다."));

        // then
        assertThatThrownBy(() -> seatFacade.getConcernedSeats(1L)).isInstanceOf(IllegalArgumentException.class);
    }


}
