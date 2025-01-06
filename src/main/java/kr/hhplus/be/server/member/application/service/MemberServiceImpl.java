package kr.hhplus.be.server.member.application.service;

import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.member.infrastructure.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository memberRepository;

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Integer> getBalance(Long id) {
        return memberRepository.findById(id).map(Member::getBalance);
    }

    @Override
    public Integer chargeBalance(Long id, Integer amount) {
        return memberRepository.findById(id).map(member -> {member.chargeBalance(amount);
            memberRepository.save(member);
            return member.getBalance();  // 충전 후 잔액 반환
        }).orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }

    @Override
    public Integer reduceBalance(Long id, Integer amount) {
        return memberRepository.findById(id).map(member -> {member.reduceBalance(amount);
            memberRepository.save(member);
            return member.getBalance();  // 충전 후 잔액 반환
        }).orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }
}
