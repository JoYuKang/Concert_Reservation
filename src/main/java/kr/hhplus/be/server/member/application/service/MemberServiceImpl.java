package kr.hhplus.be.server.member.application.service;

import kr.hhplus.be.server.member.domain.Member;
import kr.hhplus.be.server.member.domain.MemberService;
import kr.hhplus.be.server.member.infrastructure.MemberJpaRepository;
import kr.hhplus.be.server.member.interfaces.dto.request.MemberCreateRequest;
import kr.hhplus.be.server.member.interfaces.dto.request.MemberRequest;
import kr.hhplus.be.server.support.exception.DuplicateException;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.InvalidIdException;
import kr.hhplus.be.server.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository memberRepository;

    @Override
    public Member findById(Long id) {
        if(id < 1) throw new InvalidIdException(ErrorMessages.INVALID_ID);
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.MEMBER_NOT_FOUND));
    }

    @Override
    public String createMember(MemberCreateRequest request) {
        List<Member> duplicatedMember = memberRepository.findByName(request.getName());
        if(!duplicatedMember.isEmpty()) throw new DuplicateException(ErrorMessages.DUPLICATED_MEMBER);
        Member member = new Member(request.getName(), 0);
        return memberRepository.save(member).getName();
    }


    @Override
    public Member chargeBalance(MemberRequest request) {

        return memberRepository.findMemberWithLock(request.getId())
                .map(member -> {
                    member.chargeBalance(request.getAmount());  // 잔액 충전
                    return memberRepository.save(member);  // 저장 후 수정된 Member 반환
                })
                .orElseThrow(() -> new NotFoundException(ErrorMessages.MEMBER_NOT_FOUND));
    }

    @Override
    public Member reduceBalance(Long id, Integer amount) {
        return memberRepository.findMemberWithLock(id)
                .map(member -> {
                    member.reduceBalance(amount);  // 잔액 차감
                    return memberRepository.save(member);  // 저장 후 수정된 Member 반환
                })
                .orElseThrow(() -> new NotFoundException(ErrorMessages.MEMBER_NOT_FOUND));
    }

}
