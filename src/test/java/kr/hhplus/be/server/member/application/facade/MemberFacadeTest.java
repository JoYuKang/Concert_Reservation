package kr.hhplus.be.server.member.application.facade;

import jakarta.persistence.OptimisticLockException;
import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;
import kr.hhplus.be.server.support.exception.AmountInvalidException;
import kr.hhplus.be.server.support.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Testcontainers
@Sql(scripts = "/data.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberFacadeTest {

    @Autowired
    private MemberFacade memberFacade;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("맴버의 금액을 추가할 때 맴버가 존재하지 않으면 실패한다.")
    void failedChargeBalanceNotFoundMemberWithHistory() {
        // given
        MemberRequest memberRequest = new MemberRequest(9999L, 30000);

        // when, then
        assertThatThrownBy(() -> memberFacade.chargeBalanceWithHistory(memberRequest)).isInstanceOf(NotFoundException.class);

    }

    @Test
    @DisplayName("맴버의 금액을 추가할 때 맴버의 ID가 유효하지 않으면 실패한다.")
    void failedChargeBalanceWithHistory() {
        // given
        MemberRequest memberRequest = new MemberRequest(-1L, 30000);

        // when, then
        assertThatThrownBy(() -> memberFacade.chargeBalanceWithHistory(memberRequest)).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("맴버의 금액을 추가할 때 금액이 음수이면 실패한다.")
    void failedChargeMinusBalanceWithHistory() {
        // given
        MemberRequest memberRequest = new MemberRequest(1L, -30000);

        // when, then
        assertThatThrownBy(() -> memberFacade.chargeBalanceWithHistory(memberRequest)).isInstanceOf(AmountInvalidException.class);
    }

    @Test
    @DisplayName("맴버의 금액을 추가할 수 있다.")
    void chargeBalanceWithHistory() {
        // given ('Bob', 30000);
        MemberRequest memberRequest = new MemberRequest(2L, 30000);

        // when, then
        assertThat(memberFacade.chargeBalanceWithHistory(memberRequest)).isEqualTo(60000);
    }

    @Test
    @DisplayName("맴버의 금액을 여러번 추가 시 모두 성공한다.")
    void chargeMultiBalanceWithHistory() throws InterruptedException {
        // given ('Alice', 50000),
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5); // 스레드 수와 동일하게 설정
        MemberRequest memberRequest = new MemberRequest(1L, 30000);

        // when
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                try {
                    memberFacade.chargeBalanceWithHistory(memberRequest);
                } catch (Exception e) {
                    log.info("err >> {}", e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await(); // 모든 스레드가 완료될 때까지 대기
        executor.shutdown();

        // then
        assertThat(memberService.findById(1L).getBalance()).isEqualTo(200000); // 최종 잔액 확인
    }


}
