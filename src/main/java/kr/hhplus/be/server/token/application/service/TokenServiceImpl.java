package kr.hhplus.be.server.token.application.service;

import kr.hhplus.be.server.token.domain.Token;
import kr.hhplus.be.server.token.domain.TokenRepository;
import kr.hhplus.be.server.token.domain.TokenService;
import kr.hhplus.be.server.token.domain.TokenStatus;
import kr.hhplus.be.server.token.infrastructure.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public Token getToken() {
        Token token = new Token(UUID.randomUUID().toString(), TokenStatus.대기, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        return tokenJpaRepository.save(token);
    }

    @Override
    public Boolean checkToken(String tokenUUID) {
        return null;
    }


}
