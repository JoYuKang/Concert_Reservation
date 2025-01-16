package kr.hhplus.be.server.reservation.domain;

import java.util.List;

public interface ReservationService {

    List<Reservation> findByMemberId(Long memberId);

    List<Reservation> getOverTime();

    Reservation save(Reservation reservation);

    Reservation confirmReservation(Long id);

    Reservation cancelReservation(Long id);

    Reservation getReservationById(Long id);
}
