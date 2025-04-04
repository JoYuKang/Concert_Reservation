package kr.hhplus.be.server.reservation.application.facade;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.domain.ConcertService;
import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationService;
import kr.hhplus.be.server.reservation.interfaces.request.ReservationRequest;
import kr.hhplus.be.server.seat.domain.Seat;
import kr.hhplus.be.server.seat.domain.SeatService;
import kr.hhplus.be.server.support.infra.lock.DistributedLock;
import kr.hhplus.be.server.support.infra.lock.type.LockType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @DistributedLock(lockType = LockType.SEAT, key = "#request.concertId + '-' + #request.seatNumbers")
    @Transactional
    public Reservation createReservation(ReservationRequest request) {

        // member 확인
        Member member = memberService.findById(request.getMemberId());

        // concert 확인
        Concert concert = concertService.getById(request.getConcertId());

        // 판매중인 Seat 확인
        List<Seat> seats = seatService.searchSeat(request.getConcertId(), request.getSeatNumbers());

        // 예약 결제대기 저장
        Reservation reservation = new Reservation(member, concert, seats);

        return reservationService.save(reservation);
    }

}
