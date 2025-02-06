package kr.hhplus.be.server.token.application.scheduler;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.token.application.service.TokenRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenRedisScheduler {

    private final TokenRedisService tokenRedisService;
    private static final int BATCH_SIZE = 500; // 한 번에 활성화할 토큰 수

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void activateTokens() {
        log.info("Token activation scheduler started");
        Long currentActiveTokens = tokenRedisService.getActiveTokenCount();

        if (currentActiveTokens < BATCH_SIZE) {
            int tokensToActivate = Math.min(BATCH_SIZE, BATCH_SIZE - currentActiveTokens.intValue());
            tokenRedisService.activateTokens(tokensToActivate);
            log.info("Activated {} tokens", tokensToActivate);
        } else {
            log.info("Maximum number of active tokens reached. No new tokens activated.");
        }
    }

}
