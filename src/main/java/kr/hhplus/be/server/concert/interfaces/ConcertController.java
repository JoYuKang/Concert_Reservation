package kr.hhplus.be.server.concert.interfaces;

import kr.hhplus.be.server.concert.domain.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    // 콘서트 일자 조회

    // 콘서트 이름, 일자 조회

}
