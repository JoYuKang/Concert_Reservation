package kr.hhplus.be.server.seat.application.service;

import jakarta.persistence.OptimisticLockException;
import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatService;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import kr.hhplus.be.server.seat.infrastructure.SeatJpaRepository;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.SeatInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatJpaRepository seatJpaRepository;

    private boolean isRetryable = true;

    @Override
    public List<Seat> getSeats(Concert concert) {
        return seatJpaRepository.findByConcertAndStatus(concert, SeatStatus.AVAILABLE);
    }

    @Override
    public void saveSeatAll(List<Seat> seats) {
        seatJpaRepository.saveAll(seats);
    }

    @Override
    public List<Seat> searchSeatWithLock(Long concertId, List<Integer> seatNumbers) {

        // 요청된 좌석 가져오기
        List<Seat> selectedSeat = seatJpaRepository.findByConcertIdAndPositionWithLock(concertId, seatNumbers);
        // 가져온 좌석 판매중인지 확인
        // 매진인 좌석이 포함되어있으면 Exception
        // 전부 판매중인 좌석이라면 해당 좌석 매진으로 변경 후 저장
        for (Seat seat : selectedSeat) {
            if (seat.getStatus() == SeatStatus.SOLD_OUT) {
                throw new SeatInvalidException(ErrorMessages.SEAT_INVALID);
            }
            seat.reserve();
        }

        return seatJpaRepository.saveAll(selectedSeat);
    }

    @Retryable(
            retryFor = OptimisticLockException.class,
            maxAttempts = 2,
            backoff = @Backoff(delay = 300)
    )
    public List<Seat> searchSeat(Long concertId, List<Integer> seatNumbers) {

        if (!isRetryable) {
            throw new OptimisticLockException("Retry attempts exceeded.");
        }

        try {
            // 요청된 좌석 가져오기
            List<Seat> selectedSeat = seatJpaRepository.findByConcertIdAndPosition(concertId, seatNumbers);

            for (Seat seat : selectedSeat) {
                if (seat.getStatus() == SeatStatus.SOLD_OUT) {
                    throw new SeatInvalidException(ErrorMessages.SEAT_INVALID);
                }
                seat.reserve();
            }

            // 변경된 좌석을 즉시 반영하기 위해 flush() 호출
            List<Seat> savedSeats = seatJpaRepository.saveAll(selectedSeat);
            seatJpaRepository.flush();  // flush()를 사용해 바로 반영

            return savedSeats;
        } catch (OptimisticLockException ex) {
            // 첫 번째 요청에서만 재시도 처리
            if (isRetryable) {
                isRetryable = false; // 재시도 로직 이후 재시도 방지
                throw ex;
            } else {
                // 두 번째 요청부터는 예외를 처리하지 않고 실패하도록 처리
                throw new OptimisticLockException("예약에 실패했습니다.");
            }
        }
    }


}
