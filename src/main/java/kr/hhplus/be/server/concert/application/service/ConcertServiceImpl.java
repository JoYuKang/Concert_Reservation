package kr.hhplus.be.server.concert.application.service;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.domain.ConcertService;
import kr.hhplus.be.server.concert.infrastructure.ConcertJpaRepository;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.InvalidIdException;
import kr.hhplus.be.server.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public List<Concert> findByTitle(String title) {
        return concertJpaRepository.findByTitleContaining(title);
    }
    @Override
    public List<Concert> findByDate(LocalDate date) {
        return concertJpaRepository.findByConcertDate(date);
    }
    @Override
    public Concert getById(Long id) {
        if (id < 1) throw new InvalidIdException(ErrorMessages.INVALID_ID);
        return concertJpaRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.CONCERT_NOT_FOUND));
    }
}
