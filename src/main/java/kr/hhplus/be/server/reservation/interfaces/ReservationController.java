package kr.hhplus.be.server.reservation.interfaces;

import kr.hhplus.be.server.reservation.domain.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 조회

    // 예약 신청

    // 예약 취소

}
