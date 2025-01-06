package kr.hhplus.be.server.concert.infrastructure;

import kr.hhplus.be.server.concert.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConcertJPARepository extends JpaRepository<Concert, Long> {

    List<Concert> findByTitle(String name);

    List<Concert> findByConcertDate(Date date);
}
