package kr.hhplus.be.server.concert.interfaces;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.domain.ConcertService;
import kr.hhplus.be.server.concert.interfaces.dto.response.ConcertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    // 콘서트 일자 조회
    @GetMapping("/date/{date}")
    public ResponseEntity<Page<ConcertResponse>> getConcerts(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(concertService.findByDate(date, offset, limit).map(ConcertResponse::new));
    }

    // 콘서트 이름 조회
    @GetMapping("/title/{title}")
    public ResponseEntity<Page<ConcertResponse>> getConcertTitle(
            @PathVariable("title") String title,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {

        return ResponseEntity.ok(concertService.findByTitle(title, offset, limit).map(ConcertResponse::new));
    }

    // 콘서트 메인 페이지 조회
    @GetMapping("/first/{date}")
    public ResponseEntity<List<ConcertResponse>> getMainConcerts(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return ResponseEntity.ok(concertService.findByDate(date).stream().map(ConcertResponse::new).toList());
    }


}
