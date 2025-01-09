package kr.hhplus.be.server.reservation.application.facade;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.domain.ConcertService;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationService;
import kr.hhplus.be.server.reservation.interfaces.request.ReservationRequest;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationFacade {

    private final ReservationService reservationService;

    private final MemberService memberService;

    private final ConcertService concertService;

    private final SeatService seatService;

    // 좌석 예약 조회
    public List<Reservation> findMemberReservation(Long memberId) {
        // member 확인
        memberService.findById(memberId);
        // 예약 내역 반환
        return reservationService.findByMemberId(memberId);
    }

    // 좌석 예약
    public Reservation createReservation(Long memberId, ReservationRequest request) {

        // member 확인
        Member member = memberService.findById(memberId);

        // concert 확인
        Concert concert = concertService.getById(request.getConcertId());

        //  판매중인 Seat 확인
        List<Seat> availableSeats = seatService.getSeats(concert);

        // 요청된 좌석 번호
        List<Integer> requestedSeatNumbers = request.getSeatNumbers();

        // 판매중인 좌석 번호 추출
        List<Integer> availableSeatNumbers = availableSeats.stream()
                .map(Seat::getSeatNumber)
                .toList();

        // 요청된 좌석 번호가 판매중 좌석에 포함되는지 확인
        if (!new HashSet<>(availableSeatNumbers).containsAll(requestedSeatNumbers)) {
            throw new IllegalArgumentException("요청된 좌석 중 유효하지 않은 좌석이 포함되어 있습니다.");
        }
        // 요청된 좌석을 Seat 엔티티로 변환
        List<Seat> selectedSeats = availableSeats.stream()
                .filter(seat -> requestedSeatNumbers.contains(seat.getSeatNumber()))
                .toList();

        // 예약 확인
        Reservation reservation = new Reservation(member, concert, selectedSeats);

        return reservationService.save(reservation);
    }

}
