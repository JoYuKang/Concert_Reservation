package kr.hhplus.be.server.token.infrastructure;

import kr.hhplus.be.server.token.domain.Token;
import kr.hhplus.be.server.token.domain.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findByStatus(TokenStatus status);

    @Query("SELECT t FROM Token t WHERE t.status = 'INACTIVE' ORDER BY t.createTime ASC LIMIT:limit")
    List<Token> findInactiveTokens(@Param("limit") int limit);

    @Query("SELECT t FROM Token t WHERE t.expireTime < CURRENT_TIMESTAMP AND t.status = 'ACTIVE'")
    List<Token> findExpiredTokens();
}
