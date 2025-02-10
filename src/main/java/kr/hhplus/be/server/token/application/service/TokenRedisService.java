package kr.hhplus.be.server.token.application.service;

import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TokenRedisService {
    private static final String WAITING_TOKEN_KEY = "WaitingToken";
    private static final String ACTIVE_TOKEN_KEY = "ActiveToken";
    private static final Duration WAITING_TOKEN_TTL = Duration.ofHours(6);
    private static final Duration ACTIVE_TOKEN_TTL = Duration.ofMinutes(5);
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 대기열에 토큰 추가 (WAITING 상태)
     * ZSet에 토큰을 추가할 때 score는 System.currentTimeMillis()로 설정
     */
    public String addWaitingToken() {
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForZSet().add(WAITING_TOKEN_KEY, token, System.currentTimeMillis());
        redisTemplate.expire(WAITING_TOKEN_KEY, WAITING_TOKEN_TTL);
        return token;
    }

    /**
     * 활성화된 토큰을 추가 (ACTIVE 상태)
     */
    public void activateTokens(int batchSize) {
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().popMin(WAITING_TOKEN_KEY, batchSize);

        if (typedTuples != null && !typedTuples.isEmpty()) {
            for (ZSetOperations.TypedTuple<Object> tuple : typedTuples) {
                String token = tuple.getValue().toString();
                redisTemplate.opsForSet().add(ACTIVE_TOKEN_KEY, token);
                redisTemplate.expire(ACTIVE_TOKEN_KEY, ACTIVE_TOKEN_TTL);
            }
        }
    }

    /**
     * 활성화된 토큰의 개수 조회
     */
    public Long getActiveTokenCount() {
        return redisTemplate.opsForSet().size(ACTIVE_TOKEN_KEY);
    }

    /**
     * 특정 활성화된 토큰 삭제
     */
    public void removeActiveToken(String token) {
        redisTemplate.opsForSet().remove(ACTIVE_TOKEN_KEY, token);
    }

    /**
     * 대기 중인 토큰의 순서를 확인
     */
    public long getWaitingTokenIndex(String token) {
        Long rank = redisTemplate.opsForZSet().rank(WAITING_TOKEN_KEY, token);
        if (rank == null) {
            throw new NotFoundException(ErrorMessages.TOKEN_NOT_FOUND );
        }
        return rank + 1;
    }

    /**
     * 토큰이 활성 상태인지 확인
     */
    public boolean isActiveToken(String token) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(ACTIVE_TOKEN_KEY, token));
    }


}
