package kr.hhplus.be.server.reservation.application.service;

import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationService;
import kr.hhplus.be.server.reservation.domain.ReservationStatus;
import kr.hhplus.be.server.reservation.infrastructure.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationJpaRepository reservationRepository;


    @Override
    public List<Reservation> findByMemberId(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation confirmReservation(Long id) {
        Reservation reservation = findById(id);
        return reservationRepository.save(reservation.updateStatus(ReservationStatus.확정));
    }

    @Override
    public Reservation cancelReservation(Long id) {
        Reservation reservation = findById(id);

        return reservationRepository.save( reservation.updateStatus(ReservationStatus.취소));
    }

    @Override
    public Reservation getReservationById(Long id) {
        if (id < 1) throw new RuntimeException();
        return reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("예약을 찾을 수 없습니다."));
    }

    private Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("예약을 찾을 수 없습니다."));
    }
}
