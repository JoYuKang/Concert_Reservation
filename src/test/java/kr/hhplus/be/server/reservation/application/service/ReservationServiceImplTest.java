package kr.hhplus.be.server.reservation.application.service;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationStatus;
import kr.hhplus.be.server.reservation.infrastructure.ReservationJpaRepository;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import kr.hhplus.be.server.support.common.Timestamped;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.ExpiredException;
import kr.hhplus.be.server.support.exception.PaymentStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationJpaRepository reservationJpaRepository;

    @Test
    @DisplayName("유저에 대한 예약 내역을 조회할 수 있다.")
    void findByMemberId() {
        // given
        Member member = new Member(1L,"test member", 10000);
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(1L, concert, 15, 50000, SeatStatus.AVAILABLE);
        seats.add(seat);
        Reservation reservation = new Reservation(1L, member, concert, seats, 50000, ReservationStatus.AWAITING_PAYMENT);
        // when
        when(reservationJpaRepository.findByMemberId(member.getId())).thenReturn(List.of(reservation));

        // then
        assertThat(reservationService.findByMemberId(member.getId())).isEqualTo(List.of(reservation));
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        // given
        Member member = new Member(1L,"test member", 10000);
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(1L, concert, 15, 50000, SeatStatus.AVAILABLE);
        seats.add(seat);
        Reservation reservation = new Reservation(1L, member, concert, seats, 50000, ReservationStatus.AWAITING_PAYMENT);
        // when
        when(reservationJpaRepository.save(reservation)).thenReturn(reservation);

        // then
        assertThat(reservationService.save(reservation)).isEqualTo(reservation);
    }

    @Test
    @DisplayName("예약을 확정으로 변경한다.")
    void confirmReservation() {
        // given
        Member member = new Member(1L,"test member", 10000);
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(1L, concert, 15, 50000, SeatStatus.AVAILABLE);
        seats.add(seat);
        Reservation reservation = new Reservation(1L, member, concert, seats, 50000, ReservationStatus.AWAITING_PAYMENT);
        // when
        when(reservationJpaRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationJpaRepository.save(reservation)).thenReturn(reservation);

        // then
        assertThat(reservationService.confirmReservation(1L).getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
    }

    @Test
    @DisplayName("결제대기가 아닌 예약을 확정으로 변경 시 변경에 실패한다.")
    void failedConfirmReservation() {
        // given
        Member member = new Member(1L,"test member", 10000);
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(1L, concert, 15, 50000, SeatStatus.AVAILABLE);
        seats.add(seat);
        Reservation reservation = new Reservation(1L, member, concert, seats, 50000, ReservationStatus.CONFIRMED);
        // when
        when(reservationJpaRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationJpaRepository.save(reservation)).thenReturn(reservation);

        // then
        assertThatThrownBy(() -> reservationService.confirmReservation(1L)).isInstanceOf(PaymentStatusException.class);
    }

    @Test
    @DisplayName("예약을 취소로 변경한다.")
    void cancelReservation() {
        // given
        Member member = new Member(1L,"test member", 10000);
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(1L, concert, 15, 50000, SeatStatus.AVAILABLE);
        seats.add(seat);
        Reservation reservation = new Reservation(1L, member, concert, seats, 50000, ReservationStatus.AWAITING_PAYMENT);
        // when
        when(reservationJpaRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationJpaRepository.save(reservation)).thenReturn(reservation);

        // then
        assertThat(reservationService.cancelReservation(1L).getStatus()).isEqualTo(ReservationStatus.CANCELLED);
    }

    @Test
    @DisplayName("결제대기가 아닌 예약을 취소로 변경 시 변경에 실패한다.")
    void failedCancelReservation() {
        // given
        Member member = new Member(1L,"test member", 10000);
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(1L, concert, 15, 50000, SeatStatus.AVAILABLE);
        seats.add(seat);
        Reservation reservation = new Reservation(1L, member, concert, seats, 50000, ReservationStatus.CANCELLED);
        // when
        when(reservationJpaRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationJpaRepository.save(reservation)).thenReturn(reservation);

        // then
        assertThatThrownBy(() -> reservationService.cancelReservation(1L)).isInstanceOf(PaymentStatusException.class);
    }

    @Test
    @DisplayName("예약 유효 시간이 지난 예약은 유효하지 않다.")
    void failedValidateReservation() throws NoSuchFieldException, IllegalAccessException {
        // given
        Member member = new Member(1L,"test member", 10000);
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(1L, concert, 15, 50000, SeatStatus.AVAILABLE);
        seats.add(seat);
        Reservation reservation = new Reservation(1L, member, concert, seats, 50000, ReservationStatus.AWAITING_PAYMENT);
        Field createTimeField = Timestamped.class.getDeclaredField("createTime");
        createTimeField.setAccessible(true);
        createTimeField.set(reservation,  LocalDateTime.now().minusMinutes(10));

        // when, then
        assertThatThrownBy(() -> reservationService.validate(reservation)).isInstanceOf(ExpiredException.class);
    }
}
