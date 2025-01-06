package kr.hhplus.be.server.member.domain;

import java.util.Optional;

public interface MemberService {

    // 유저 조회
    Optional<Member> findById(Long id);

    // 유저 금액 조회 (금액만 반환)
    Optional<Integer> getBalance(Long id);

    // 유저 금액 충전 후 잔액 반환
    Integer chargeBalance(Long id, Integer amount);

    // 유저 금액 차감 후 잔액 반환
    Integer reduceBalance(Long id, Integer amount);
}
