package kr.hhplus.be.server.seat.application.service;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatService;
import kr.hhplus.be.server.seat.domain.SeatStatus;
import kr.hhplus.be.server.seat.infrastructure.SeatJpaRepository;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.SeatInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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

}
