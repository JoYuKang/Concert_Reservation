package kr.hhplus.be.server.seat.application.service;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import kr.hhplus.be.server.seat.infrastructure.SeatJpaRepository;
import kr.hhplus.be.server.support.exception.NotFoundException;
import kr.hhplus.be.server.support.exception.SeatInvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        Seat seat = new Seat(1L, concert, 14, 50000, SeatStatus.AVAILABLE);
        // when
        when(seatJpaRepository.findByConcertAndStatus(concert, SeatStatus.AVAILABLE)).thenReturn(List.of(seat));
        // then
        assertThat(seatService.getSeats(concert)).isEqualTo(List.of(seat));
    }

    @Test
    @DisplayName("콘서트의 에약 가능한 좌석이 없다면 공백을 리턴한다.")
    void getConcernedSeatsZero() {
        // given
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        // when
        when(seatJpaRepository.findByConcertAndStatus(concert, SeatStatus.AVAILABLE)).thenReturn(List.of());
        // then
        assertThat(seatService.getSeats(concert).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("좌석 예약 중 매진된 좌석이 있다면 예약에 실패한다.")
    void failedGetConcernedSeats() {
        // given
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        Seat seat1 = new Seat(1L, concert, 14, 50000, SeatStatus.AVAILABLE);
        Seat seat2 = new Seat(2L, concert, 15, 50000, SeatStatus.SOLD_OUT);

        // when
        when(seatJpaRepository.findByConcertIdAndPositionWithLock(concert.getId(), List.of(14, 15))).thenReturn(List.of(seat1, seat2));

        // then
        assertThatThrownBy(() -> seatService.searchSeatWithLock(concert.getId(),List.of(14, 15))).isInstanceOf(SeatInvalidException.class);
    }

}
