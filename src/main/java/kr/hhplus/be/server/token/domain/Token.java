package kr.hhplus.be.server.token.domain;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    private LocalDateTime createTime;

    private LocalDateTime expireTime;

    public Token(String token) {
        this.token = token;
        this.createTime = LocalDateTime.now();
        this.expireTime = LocalDateTime.now().plusMinutes(30);
        this.status = TokenStatus.INACTIVE;
    }

    // 상태를 변경하는 메서드
    public void expire() {
        this.status = TokenStatus.EXPIRED;
    }

    public void active() {
        if (this.status == TokenStatus.EXPIRED) {
            throw new IllegalStateException("이미 만료된 토큰입니다.");
        }
        this.status = TokenStatus.ACTIVE;
    }

}
