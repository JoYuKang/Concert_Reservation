package kr.hhplus.be.server.token.application.service;

import kr.hhplus.be.server.token.domain.Token;
import kr.hhplus.be.server.token.domain.TokenService;
import kr.hhplus.be.server.token.infrastructure.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenJpaRepository tokenJpaRepository;

    public static final int ACTIVATION_LIMIT = 500;

    @Override
    public Token get(String token) {
        return tokenJpaRepository.findByToken(token).orElseThrow(() -> new RuntimeException("토큰이 존재하지 않습니다."));
    }

    @Override
    public String create() {
        return tokenJpaRepository.save(new Token(UUID.randomUUID().toString())).getToken();
    }

    @Override
    public List<Token> active(List<Token> tokens) {
        for (Token token : tokens) {
            token.active();
        }
        return tokenJpaRepository.saveAll(tokens);
    }

    @Override
    public Token expire(String token) {
        Token activeToken = tokenJpaRepository.findByToken(token).orElseThrow(() -> new RuntimeException("토큰이 존재하지 않습니다."));
        activeToken.expire();
        return tokenJpaRepository.save(activeToken);
    }

    @Override
    public List<Token> findInactiveTokens() {
        return tokenJpaRepository.findInactiveTokens(ACTIVATION_LIMIT);
    }

    public List<Token> findExpiredTokens() {
        return tokenJpaRepository.findExpiredTokens();
    }

}
