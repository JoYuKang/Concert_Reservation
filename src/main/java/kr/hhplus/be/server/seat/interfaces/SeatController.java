package kr.hhplus.be.server.seat.interfaces;

import kr.hhplus.be.server.seat.domain.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    // 좌석 조회

}
