package kr.hhplus.be.server.token.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.ExpiredException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Table(name = "tb_token")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    @Column(name = "created_at")
    private LocalDateTime createTime;

    @Column(name = "expired_at")
    private LocalDateTime expireTime;

    public Token(String token) {
        this.token = token;
        this.createTime = LocalDateTime.now();
        this.status = TokenStatus.INACTIVE;
    }

    // 상태를 변경하는 메서드
    public void expire() {
        this.status = TokenStatus.EXPIRED;
    }

    public void active() {
        if (this.status == TokenStatus.EXPIRED) {
            throw new ExpiredException(ErrorMessages.TOKEN_EXPIRED);
        }
        this.expireTime = LocalDateTime.now().plusMinutes(30);
        this.status = TokenStatus.ACTIVE;
    }

}
