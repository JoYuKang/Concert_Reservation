package kr.hhplus.be.server.concert.application.service;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.domain.ConcertService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConcertServiceImpl implements ConcertService {

    @Override
    public List<Concert> findByTitleLike(String title) {
        return List.of();
    }

    @Override
    public List<Concert> findByDate(Date date) {
        return List.of();
    }
}
