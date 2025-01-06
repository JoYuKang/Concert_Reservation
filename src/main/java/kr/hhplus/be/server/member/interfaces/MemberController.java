package kr.hhplus.be.server.member.interfaces;

import kr.hhplus.be.server.member.domain.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 맴버 조회

    // 맴버 잔액 조회

    // 맴버 잔액 충전

}
