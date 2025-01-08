package kr.hhplus.be.server.reservation.interfaces;

import kr.hhplus.be.server.reservation.application.facade.ReservationFacade;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationService;
import kr.hhplus.be.server.reservation.interfaces.request.ReservationRequest;
import kr.hhplus.be.server.reservation.interfaces.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationFacade reservationFacade;

    // 유저의 예약 조회
    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<ReservationResponse>> getMemberReservations(@PathVariable("id") Long memberId) {
        return new ResponseEntity<>(reservationFacade.findMemberReservation(memberId).stream()
                .map(ReservationResponse::new)
                .toList(), HttpStatus.OK);
    }

    // 예약 신청
    @PostMapping("/{id}/reservations")
    public ResponseEntity<ReservationResponse> addReReservation(@PathVariable("id") Long memberId, @RequestBody ReservationRequest request) {
        return new ResponseEntity<>(new ReservationResponse(reservationFacade.createReservation(memberId, request)), HttpStatus.CREATED);
    }

}
