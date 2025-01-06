package kr.hhplus.be.server.concert.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ConcertService {

    // 이름 조회
    List<Concert> findByTitle(String title);

    // 날짜 조회
    List<Concert> findByDate(LocalDate date);

    // 콘서트 ID 조회
    Concert getById(Long id);
}
