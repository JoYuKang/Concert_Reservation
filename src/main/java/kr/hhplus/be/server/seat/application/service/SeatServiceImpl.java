package kr.hhplus.be.server.seat.application.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatService;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import kr.hhplus.be.server.seat.infrastructure.SeatJpaRepository;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.SeatInvalidException;
import kr.hhplus.be.server.support.infra.lock.DistributedLockAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> getSeats(Concert concert) {
        return seatJpaRepository.findByConcertAndStatus(concert, SeatStatus.AVAILABLE);
    }

    @Override
    public void saveSeatAll(List<Seat> seats) {
        seatJpaRepository.saveAll(seats);
    }

    @DistributedLockAspect(key = "#concertId + '-' + #seatNumbers")
    @Transactional
    @Override
    public List<Seat> searchSeat(Long concertId, List<Integer> seatNumbers) {
        try {
            // 요청된 좌석 가져오기
            List<Seat> selectedSeat = seatJpaRepository.findByConcertIdAndPosition(concertId, seatNumbers);
            // 좌석 유효성 확인
            for (Seat seat : selectedSeat) {
                if (seat.getStatus() == SeatStatus.SOLD_OUT) {
                    throw new SeatInvalidException(ErrorMessages.SEAT_INVALID);
                }
                seat.reserve();
            }
            return seatJpaRepository.saveAll(selectedSeat);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new SeatInvalidException(ErrorMessages.SEAT_NOT_FOUND);
        }
    }


}
