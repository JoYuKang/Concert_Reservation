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
    private static final int BATCH_SIZE = 20; // 한 번에 활성화할 토큰 수
    private static final int MAX_BATCH_SIZE = 700; // 최대 활성화할 토큰 수

    @Transactional
    @Scheduled(fixedRate = 5000)
    public void activateTokens() {
        log.info("Token activation scheduler started");
        Long currentActiveTokens = tokenRedisService.getActiveTokenCount();

        if (currentActiveTokens < MAX_BATCH_SIZE) {
            tokenRedisService.activateTokens(BATCH_SIZE);
        } else {
            log.info("최대 활성 토큰 수에 도달하여 새로운 토큰을 활성화하지 않음.");
        }
    }

}
