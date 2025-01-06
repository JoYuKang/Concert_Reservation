package kr.hhplus.be.server.concert.domain;


import java.util.Date;
import java.util.List;

public interface ConcertService {

    // 이름 조회
    List<Concert> findByTitleLike(String title);

    // 날짜 조회
    List<Concert> findByDate(Date date);

}
