package kr.hhplus.be.server.token.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(scripts = "/data.sql")
public class TokenRedisTest {

    @Autowired
    private TokenRedisService tokenRedisService;

    @Test
    @DisplayName("토큰을 생성 후 대기열에 들어간다.")
    void testTokenCreate() {
        String token = tokenRedisService.addWaitingToken();
        long waitingTokenIndex = tokenRedisService.getWaitingTokenIndex(token);

        assertThat(waitingTokenIndex).isGreaterThanOrEqualTo(1);
    }


}
