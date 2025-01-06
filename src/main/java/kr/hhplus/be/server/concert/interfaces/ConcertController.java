package kr.hhplus.be.server.concert.interfaces;

import kr.hhplus.be.server.concert.domain.ConcertService;
import kr.hhplus.be.server.concert.interfaces.dto.response.ConcertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    // 콘서트 일자 조회
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ConcertResponse>> getConcerts(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date ) {
        return new ResponseEntity<>(concertService.findByDate(date).stream().map(ConcertResponse::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    // 콘서트 이름 조회
    @GetMapping("/title/{title}")
    public ResponseEntity<List<ConcertResponse>> getConcertTitle(@PathVariable String title ) {
        return new ResponseEntity<>(concertService.findByTitle(title).stream().map(ConcertResponse::new).collect(Collectors.toList()), HttpStatus.OK);
    }

}
