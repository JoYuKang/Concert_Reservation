package kr.hhplus.be.server.token.application.service;

import kr.hhplus.be.server.token.domain.Token;
import kr.hhplus.be.server.token.domain.TokenStatus;
import kr.hhplus.be.server.token.infrastructure.TokenJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private TokenJpaRepository tokenJpaRepository;


    @Test
    @DisplayName("토큰을 조회할 수 있다.")
    void get() {
        // given
        String uuid = UUID.randomUUID().toString();
        Token token = new Token(uuid);
        // when
        when(tokenJpaRepository.findByToken(uuid)).thenReturn(Optional.of(token));
        // then
        assertThat(tokenService.get(uuid)).isEqualTo(token);
    }

    @Test
    @DisplayName("토큰이 존재하지 않으면 조회에 실패한다.")
    void failedGet() {
        // given
        String uuid = UUID.randomUUID().toString();
        // when, then
        assertThatThrownBy(() -> tokenService.get(uuid)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("토큰을 생성할 수 있다.")
    void create() {
        // given
        String uuid = UUID.randomUUID().toString();
        Token token = new Token(uuid);
        // when
        when(tokenJpaRepository.save(any(Token.class))).thenReturn(token);
        // then
        assertThat(tokenService.create()).isEqualTo(token);
    }

    @Test
    @DisplayName("토큰을 활성화할 수 있다.")
    void active() {
        // given
        List<Token> tokens = List.of(new Token(UUID.randomUUID().toString()), new Token(UUID.randomUUID().toString()));
        for (Token token : tokens) {
            token.active();
        }
        // when
        when(tokenJpaRepository.saveAll(tokens)).thenReturn(tokens);

        // then
        assertThat(tokenService.active(tokens)).isEqualTo(tokens);
    }

    @Test
    @DisplayName("만료된 토큰을 활성화할 수 없다.")
    void failedActive() {
        // given
        List<Token> tokens = List.of(new Token(UUID.randomUUID().toString()), new Token(UUID.randomUUID().toString()));
        for (Token token : tokens) {
            token.expire();
        }
        // when, then
        assertThatThrownBy(() -> tokenService.active(tokens)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("토큰을 만료할 수 있다.")
    void expire() {
        // given
        String uuid = UUID.randomUUID().toString();
        Token token = new Token(uuid);
        // when
        when(tokenJpaRepository.findByToken(uuid)).thenReturn(Optional.of(token));
        when(tokenJpaRepository.save(token)).thenReturn(token);
        // then
        assertThat(tokenService.expire(uuid).getStatus()).isEqualTo(TokenStatus.EXPIRED);
    }

}
