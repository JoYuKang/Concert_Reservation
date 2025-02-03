package kr.hhplus.be.server.concert.domain;

import kr.hhplus.be.server.concert.application.service.ConcertServiceImpl;
import kr.hhplus.be.server.concert.infrastructure.ConcertJpaRepository;
import kr.hhplus.be.server.support.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @InjectMocks
    private ConcertServiceImpl concertService;

    @Mock
    private ConcertJpaRepository concertJpaRepository;

    @Test
    @DisplayName("유저가 입력한 값이 포함된 콘서트 이름을 반환한다.")
    void findByTitle() {
        // given
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());
        List<Concert> concertList = List.of(concert);
        Pageable pageable = PageRequest.of(1, 5, Sort.by("concertDate").ascending());
        Page<Concert> concertPage = new PageImpl<>(concertList, pageable, concertList.size());

        // when
        when(concertJpaRepository.findByTitleContaining("Winter", pageable)).thenReturn(concertPage);

        // then
        assertThat(concertService.findByTitle("Winter", 1, 5)).isEqualTo(concertPage);
    }
    @Test
    @DisplayName("해당 일자에 열리는 콘서트를 조회한다.")
    void findByDate() {
        // given
        LocalDate date = LocalDate.now();
        Concert concert = new Concert(1L, "Winter Concert", date);
        List<Concert> concertList = List.of(concert);
        Pageable pageable = PageRequest.of(1, 5, Sort.by("title").ascending());
        Page<Concert> concertPage = new PageImpl<>(concertList, pageable, concertList.size());
        // when
        when(concertJpaRepository.findByConcertDate(date, pageable)).thenReturn(concertPage);

        // that
        assertThat(concertService.findByDate(date, 1, 5)).isEqualTo(concertPage);
    }

    @Test
    @DisplayName("콘서트 Id로 콘서트를 조회한다.")
    void getById() {
        // given
        Concert concert = new Concert(1L, "Winter Concert", LocalDate.now());

        // when
        when(concertJpaRepository.findById(1L)).thenReturn(Optional.of(concert));

        // that
        assertThat(concertService.getById(1L)).isEqualTo(concert);
    }

    @Test
    @DisplayName("존재하지 않는 Concert Id를 입력하면 검색에 실패한다.")
    void failedGetById() {
        // when, that
        assertThatThrownBy(() ->concertService.getById(1L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("특정 일자로 콘서트를 조회한다.")
    void GetById() {
        // given
        LocalDate date = LocalDate.now();
        Concert concert = new Concert(1L, "Winter Concert", date);
        List<Concert> concertList = List.of(concert);
        // when
        when(concertJpaRepository.findAllByClosestToToday(date)).thenReturn(concertList);
        // that
        assertThat(concertService.findByDate(date)).isEqualTo(concertList);
    }
}
