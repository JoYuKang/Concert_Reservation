package kr.hhplus.be.server.token.application.scheduler;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.token.domain.Token;
import kr.hhplus.be.server.token.domain.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenActiveScheduler {

    private final TokenService tokenService;

//    @Scheduled(fixedRate = 5000)
    @Transactional
    public void activateTokens() {
        List<Token> inactiveTokens = tokenService.findInactiveTokens();
        tokenService.active(inactiveTokens);
    }
}

