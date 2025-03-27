package kr.hhplus.be.server.support.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec()); // ✅ setCodec을 Config 객체에 적용
        config.useSingleServer()
                .setAddress("redis://localhost:6379") // Redis 주소
                .setTimeout(10000) // 타임아웃 10초
                .setRetryAttempts(3) // 재시도 횟수
                .setRetryInterval(2000); // 재시도 간격 2초

        return Redisson.create(config);
    }
}
