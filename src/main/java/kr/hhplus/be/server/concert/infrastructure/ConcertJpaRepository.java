package kr.hhplus.be.server.concert.infrastructure;

import kr.hhplus.be.server.concert.domain.Concert;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

    Page<Concert> findByTitleContaining(String title, Pageable pageable);

    Page<Concert> findByConcertDate(LocalDate date, Pageable pageable);

}
