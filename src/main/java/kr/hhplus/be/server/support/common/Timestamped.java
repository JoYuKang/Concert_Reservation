package kr.hhplus.be.server.support.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {

    @CreatedBy
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createTime;

    @LastModifiedBy
    @Column(name = "updated_at")
    private LocalDateTime updateTime;
}
