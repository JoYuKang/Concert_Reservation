package kr.hhplus.be.server.reservation.application.service;

import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationService;
import kr.hhplus.be.server.reservation.domain.ReservationStatus;
import kr.hhplus.be.server.reservation.infrastructure.ReservationJpaRepository;
import kr.hhplus.be.server.support.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationJpaRepository reservationRepository;


    @Override
    public List<Reservation> findByMemberId(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }

    @Override
    public List<Reservation> getOverTime() {
        return reservationRepository.findByOverTime();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation confirmReservation(Long id) {
        Reservation reservation = findById(id);
        return reservationRepository.save(reservation.updateStatus(ReservationStatus.CONFIRMED));
    }

    @Override
    public Reservation cancelReservation(Long id) {
        Reservation reservation = findById(id);
        return reservationRepository.save(reservation.updateStatus(ReservationStatus.CANCELLED));
    }

    @Override
    public Reservation getReservationById(Long id, Long memberId) {
        Reservation reservation = findById(id);
        if (!Objects.equals(reservation.getMember().getId(), memberId)) throw new UnauthorizedReservationException(ErrorMessages.UNAUTHORIZED_RESERVATION);
        return reservation;
    }

    @Override
    public void validate(Reservation reservation) {
        if (reservation.getCreateTime().isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new ExpiredException(ErrorMessages.RESERVATION_EXPIRED);
        }
    }


    private Reservation findById(Long id) {
        if (id < 1) throw new InvalidIdException(ErrorMessages.INVALID_ID);
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.RESERVATION_NOT_FOUND));
    }
}
