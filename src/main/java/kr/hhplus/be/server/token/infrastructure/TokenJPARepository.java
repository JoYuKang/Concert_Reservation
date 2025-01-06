package kr.hhplus.be.server.token.infrastructure;

import kr.hhplus.be.server.token.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJPARepository extends JpaRepository<Token, Long> {
}
