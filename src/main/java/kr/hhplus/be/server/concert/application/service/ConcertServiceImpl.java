package kr.hhplus.be.server.concert.application.service;

import kr.hhplus.be.server.concert.domain.Concert;
import kr.hhplus.be.server.concert.domain.ConcertService;
import kr.hhplus.be.server.concert.infrastructure.ConcertJpaRepository;
import kr.hhplus.be.server.support.exception.ErrorMessages;
import kr.hhplus.be.server.support.exception.InvalidIdException;
import kr.hhplus.be.server.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public Page<Concert> findByTitle(String title, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("concertDate").ascending());  // 정렬 추가

        return concertJpaRepository.findByTitleContaining(title, pageable);
    }


    @Override
    public Page<Concert> findByDate(LocalDate date, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("title").ascending());  // 정렬 추가
        return concertJpaRepository.findByConcertDate(date, pageable);
    }

    @Override
    @Cacheable(value = "concerts", key = "'concert:' + #date")
    public List<Concert> findByDate(LocalDate date) {
        return concertJpaRepository.findAllByClosestToToday(date);
    }

    @Override
    public Concert getById(Long id) {
        if (id < 1) throw new InvalidIdException(ErrorMessages.INVALID_ID);
        return concertJpaRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.CONCERT_NOT_FOUND));
    }
}
