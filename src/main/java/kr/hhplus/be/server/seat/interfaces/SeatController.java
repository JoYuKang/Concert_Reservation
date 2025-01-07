package kr.hhplus.be.server.seat.interfaces;

import kr.hhplus.be.server.seat.application.facade.SeatFacade;
import kr.hhplus.be.server.seat.interfaces.response.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class SeatController {

    private final SeatFacade seatFacade;

    // 좌석 조회
    @GetMapping("/{concertId}/seat")
    public List<SeatResponse> getSeats(@PathVariable("concertId") Long concertId) {
        return seatFacade.getConcernedSeats(concertId).stream().map(SeatResponse::new).collect(Collectors.toList());
    }
}
