package kr.hhplus.be.server.concert.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ConcertService {

    // 제목 조회
    Page<Concert> findByTitle(String title, Integer offset, Integer limit);

    // 날짜 조회
    Page<Concert> findByDate(LocalDate date, Integer offset, Integer limit);

    // 메인 페이지 가장 가까운 콘서트 TOP 100
    List<Concert> findByDate(LocalDate date);

    // 카테고리 별 콘서트 조회
    List<Concert> getUpcomingConcertsByCategory(String category);

    // 콘서트 ID 조회
    Concert getById(Long id);
}
