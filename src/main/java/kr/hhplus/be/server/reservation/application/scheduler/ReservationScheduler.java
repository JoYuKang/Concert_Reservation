package kr.hhplus.be.server.reservation.application.scheduler;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationService;
import kr.hhplus.be.server.reservation.domain.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationService reservationService;

    // 예약 시간이 5분 이상 지난 예약에 대하여 상태 값을 취소로 변경
    // 해당 좌석을 예약 가능 상태로 변경
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void cancelReservation() {
        List<Reservation> overTimeReservations = reservationService.getOverTime();

        overTimeReservations.forEach(overTimeReservation -> {
            // 예약 상태 수정
            overTimeReservation.updateStatus(ReservationStatus.CANCELLED);
            // 좌석 상태 수정
            overTimeReservation.getSeats()
                    .forEach(seat -> seat.cancel());
        });

    }
}
