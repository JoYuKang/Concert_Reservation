package kr.hhplus.be.server.member.interfaces.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    private Long id;

    @Min(1)
    private Integer amount;
}
