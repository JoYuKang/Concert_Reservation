package kr.hhplus.be.server.concert.infrastructure;

import kr.hhplus.be.server.concert.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

    List<Concert> findByTitleContaining(String title);

    List<Concert> findByConcertDate(LocalDate date);

    Optional<Concert> findById(Long id);

}
