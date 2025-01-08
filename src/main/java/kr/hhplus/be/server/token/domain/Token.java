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

    private String tokenId;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createTime;

    @Column(name = "expired_at")
    private LocalDateTime expireTime;

    public Token(String tokenId, TokenStatus status, LocalDateTime createTime, LocalDateTime expireTime) {
        this.tokenId = tokenId;
        this.status = status;
        this.createTime = createTime;
        this.expireTime = expireTime;
    }

}
