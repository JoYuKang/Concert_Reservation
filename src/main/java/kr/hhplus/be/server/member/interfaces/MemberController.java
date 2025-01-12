package kr.hhplus.be.server.member.interfaces;

import jakarta.validation.Valid;
import kr.hhplus.be.server.member.application.facade.MemberFacade;
import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;
import kr.hhplus.be.server.member.interfaces.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    private final MemberFacade memberFacade;

    // 맴버 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findById(@PathVariable("id") Long id) {
        MemberResponse memberResponse = new MemberResponse(memberService.findById(id));
        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
    }

    // 맴버 잔액 충전
    @PostMapping("/balances")
    public ResponseEntity<Integer> charge(@Valid @RequestBody MemberRequest request) {

        return new ResponseEntity<>(memberFacade.chargeBalanceWithHistory(request), HttpStatus.OK);
    }

}
