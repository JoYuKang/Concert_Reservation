package kr.hhplus.be.server.payment.application.facade;

import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.payment.domain.Payment;
import kr.hhplus.be.server.payment.interfaces.request.PaymentRequest;
import kr.hhplus.be.server.support.exception.AmountInvalidException;
import kr.hhplus.be.server.support.exception.UnauthorizedReservationException;
import kr.hhplus.be.server.token.application.service.TokenRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Testcontainers
@Sql(scripts = "/data.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PaymentFacadeTest {

    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private TokenRedisService tokenRedisService;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("예약을 결제할 수 있다.")
    void createPayment() {
        PaymentRequest request = new PaymentRequest(1L);
        String uuid = tokenRedisService.addWaitingToken();
        tokenRedisService.activateTokens(3);
        Integer balance = memberService.findById(1L).getBalance();
        Payment payment = paymentFacade.createPayment(1L, request, uuid);
        Integer reduceBalance = memberService.findById(1L).getBalance();
        Integer amount = payment.getAmount();

        assertThat(balance - amount).isEqualTo(reduceBalance);
    }

    @Test
    @DisplayName("예약자와 결제자가 다르면 예약을 결제할 수 없다.")
    void failedOtherCreatePayment() {
        PaymentRequest request = new PaymentRequest(1L);
        String uuid = tokenRedisService.addWaitingToken();
        tokenRedisService.activateTokens(3);
        assertThatThrownBy(() -> paymentFacade.createPayment(2L, request, uuid)).isInstanceOf(UnauthorizedReservationException.class);
    }

    @Test
    @DisplayName("금액이 부족하면 예약을 결제할 수 없다.")
    void failedCreatePayment() {
        PaymentRequest request = new PaymentRequest(2L);
        String uuid = tokenRedisService.addWaitingToken();
        tokenRedisService.activateTokens(3);
        assertThatThrownBy(() -> paymentFacade.createPayment(2L, request, uuid)).isInstanceOf(AmountInvalidException.class);
    }
}