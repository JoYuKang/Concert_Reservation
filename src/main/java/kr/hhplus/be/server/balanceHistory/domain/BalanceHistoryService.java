package kr.hhplus.be.server.balanceHistory.domain;

import kr.hhplus.be.server.member.domain.Member;

public interface BalanceHistoryService {

    void insertHistory(Member member, Integer amount, BalanceStatus status);
}
