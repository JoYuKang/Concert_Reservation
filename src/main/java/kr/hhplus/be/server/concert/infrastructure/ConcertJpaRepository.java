package kr.hhplus.be.server.concert.infrastructure;

import kr.hhplus.be.server.concert.domain.Concert;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

    Page<Concert> findByTitleContaining(String title, Pageable pageable);

    Page<Concert> findByConcertDate(LocalDate date, Pageable pageable);

    // 주어진 날짜 이후의 콘서트를 조회하고, 해당 날과 가까운 날짜부터 멀어지는 순으로 정렬
    @Query("SELECT c FROM Concert c WHERE c.concertDate >= :date ORDER BY c.concertDate ASC LIMIT 100")
    List<Concert> findAllByClosestToToday(@Param("date") LocalDate date);

}
