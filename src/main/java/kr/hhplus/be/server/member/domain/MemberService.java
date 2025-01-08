package kr.hhplus.be.server.member.domain;

import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;


public interface MemberService {

    // 유저 조회
    Member findById(Long id);

    // 유저 금액 충전 후 잔액 반환
    Member chargeBalance(MemberRequest request);

    // 유저 금액 차감 후 잔액 반환
    Member reduceBalance(Long id, Integer amount);
}
