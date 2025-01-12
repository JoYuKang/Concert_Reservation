package kr.hhplus.be.server.member.interfaces.dto.response;

import kr.hhplus.be.server.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private String name;

    private Integer balance;

    public MemberResponse(Member member) {
        this.name = member.getName();
        this.balance = member.getBalance();
    }
}
