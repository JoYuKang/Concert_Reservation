package kr.hhplus.be.server.member.application.facade;

import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberFacadeTest {

    private final MemberFacade memberFacade;

    // 생성자 주입을 통한 의존성 주입
    MemberFacadeTest(MemberFacade memberFacade) {
        this.memberFacade = memberFacade;
    }

    @Test
    @DisplayName("맴버의 금액을 추가할 때 맴버가 존재하지 않으면 실패한다.")
    void failedChargeBalanceNotFoundMemberWithHistory() {
        // given
        MemberRequest memberRequest = new MemberRequest(4L, 30000);

        // when, then
        assertThatThrownBy(() -> memberFacade.chargeBalanceWithHistory(memberRequest)).isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("맴버의 금액을 추가할 때 맴버의 ID가 유효하지 않으면 실패한다.")
    void failedChargeBalanceWithHistory() {
        // given
        MemberRequest memberRequest = new MemberRequest(-1L, 30000);

        // when, then
        assertThatThrownBy(() -> memberFacade.chargeBalanceWithHistory(memberRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("맴버의 금액을 추가할 때 맴버의 ID가 유효하지 않으면 실패한다.")
    void failedChargeMinusBalanceWithHistory() {
        // given
        MemberRequest memberRequest = new MemberRequest(1L, -30000);

        // when, then
        assertThatThrownBy(() -> memberFacade.chargeBalanceWithHistory(memberRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("맴버의 금액을 추가할 때 맴버의 ID가 유효하지 않으면 실패한다.")
    void chargeBalanceWithHistory() {
        // given
        MemberRequest memberRequest = new MemberRequest(1L, 30000);

        // when, then
        assertThat(memberFacade.chargeBalanceWithHistory(memberRequest)).isEqualTo(40000);
    }
}
