package kr.hhplus.be.server.seat.application.service;

import kr.hhplus.be.server.concert.domain.Concert;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @InjectMocks
    private SeatServiceImpl seatService;

    @Mock
    private SeatJpaRepository seatJpaRepository;

    @Test
    @DisplayName("콘서트의 좌석을 조회할 수 있다.")
    void getConcernedSeats() {
        // given
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        Seat seat = new Seat(1L, concert, 14, 50000, SeatStatus.판매중);
        // when
        when(seatJpaRepository.findByConcertIdAndStatus(concert, SeatStatus.판매중)).thenReturn(List.of(seat));
        // then
        assertThat(seatService.getSeats(concert)).isEqualTo(List.of(seat));

    }

    @Test
    @DisplayName("잘못된 콘서트 ID를 입력 시 조회에 실패한다.")
    void failedInvalidConcernedSeats() {
        // given

        // when

        // then

    }

    @Test
    @DisplayName("콘서트가 존재하지 않을 시 조회에 실패한다.")
    void failedNotFoundConcernedSeats() {
        // given

        // when

        // then

    }
}
