package kr.hhplus.be.server.payment.application.service;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.payment.domain.Payment;
import kr.hhplus.be.server.payment.domain.PaymentStatus;
import kr.hhplus.be.server.payment.infrastructure.PaymentJpaRepository;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationStatus;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentJpaRepository paymentJpaRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    @DisplayName("결제를 저장한다.")
    void create() {
        // given
        Member member = new Member(1L,"test member", 10000, 0);
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now(), "POP");
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat(1L, concert, 15, 50000, SeatStatus.AVAILABLE, 0);
        seats.add(seat);
        Reservation reservation = new Reservation(1L, member, concert, seats, 50000, ReservationStatus.AWAITING_PAYMENT);
        Payment payment = new Payment(1L, member, reservation, 10000, PaymentStatus.COMPLETED);

        // when
        when(paymentJpaRepository.save(payment)).thenReturn(payment);
        // then
        assertThat(paymentService.create(payment)).isEqualTo(payment);
    }
}
