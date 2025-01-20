package kr.hhplus.be.server.token.application.service;

import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.NotFoundException;
import kr.hhplus.be.server.token.domain.Token;
import kr.hhplus.be.server.token.domain.TokenService;
import kr.hhplus.be.server.token.domain.TokenStatus;
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

        return tokenJpaRepository.findByToken(token).orElseThrow(() -> new NotFoundException(ErrorMessages.TOKEN_NOT_FOUND));
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
        Token activeToken = tokenJpaRepository.findByToken(token).orElseThrow(() -> new NotFoundException(ErrorMessages.TOKEN_NOT_FOUND));
        activeToken.expire();
        return tokenJpaRepository.save(activeToken);
    }

    @Override
    public List<Token> expireList(List<Token> tokens) {
        for (Token token : tokens) {
            token.expire();
        }
        return tokenJpaRepository.saveAll(tokens);
    }

    @Override
    public List<Token> findInactiveTokens() {
        // 현재 활성화 되어있는 토큰 확인
        List<Token> activeTokens = tokenJpaRepository.findByStatus(TokenStatus.ACTIVE);

        int activeTotalNum = ACTIVATION_LIMIT - activeTokens.size();

        // 기준 - 활성화된 토큰 = 활성화 해줘야할 토큰 가져오기
        return tokenJpaRepository.findInactiveTokens(activeTotalNum);
    }

    public List<Token> findExpiredTokens() {
        return tokenJpaRepository.findExpiredTokens();
    }

}
