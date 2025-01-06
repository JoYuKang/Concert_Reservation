package kr.hhplus.be.server.member.infrastructure;

import kr.hhplus.be.server.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJPARepository extends JpaRepository<Member, Long> {

}
