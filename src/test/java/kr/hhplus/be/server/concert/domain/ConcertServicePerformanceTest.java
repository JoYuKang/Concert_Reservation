package kr.hhplus.be.server.concert.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConcertServicePerformanceTest {

    @Autowired
    private ConcertService concertService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final StopWatch stopWatch = new StopWatch();

    private static LocalDate now = LocalDate.now();
    private static final String CACHE_KEY = "concerts::concert:" + now;

    @BeforeEach
    void setUp() {
        // Redis 캐시 비우기 (테스트를 위한 초기화)
        redisTemplate.delete(CACHE_KEY);
    }

    @Test
    @DisplayName("DB 조회로 조회한다.")
    void testDBPerformance() {

        stopWatch.start();
        concertService.findByDate(now);
        stopWatch.stop();
        log.info("DB 조회 시간(ns): {}", stopWatch.getTotalTimeNanos());

    }
    @Test
    @DisplayName("Redis 캐시 적용 후 조회한다.")
    void testRedisCachePerformance() {
        concertService.findByDate(now);
        stopWatch.start();
        concertService.findByDate(now);  // Redis에서 조회
        stopWatch.stop();

        // Redis Cache 조회 시간 확인
        log.info("Redis 캐시 조회 시간(ns): {}", stopWatch.getTotalTimeNanos());
    }
}


