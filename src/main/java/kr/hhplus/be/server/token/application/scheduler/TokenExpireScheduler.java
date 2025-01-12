package kr.hhplus.be.server.token.application.scheduler;

import kr.hhplus.be.server.token.domain.Token;
import kr.hhplus.be.server.token.domain.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenExpireScheduler {

    private final TokenService tokenService;

    @Scheduled(fixedRate = 5000)
    public void expiredTokens() {
        tokenService.findExpiredTokens();
    }

}
