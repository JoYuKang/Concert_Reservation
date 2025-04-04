package kr.hhplus.be.server.reservation.application.facade;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import kr.hhplus.be.server.reservation.domain.Reservation;
import kr.hhplus.be.server.reservation.domain.ReservationService;
import kr.hhplus.be.server.reservation.infrastructure.ReservationJpaRepository;
import kr.hhplus.be.server.reservation.interfaces.request.ReservationRequest;
import kr.hhplus.be.server.support.exception.SeatInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Testcontainers
@Sql(scripts = "/data.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ReservationFacadeTest {

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationJpaRepository reservationJpaRepository;

    @Test
    @Transactional
    @DisplayName("유저의 예약 내역을 확인할 수 있다.")
    void findMemberReservation() {
        // when
        List<Reservation> memberReservation = reservationFacade.findMemberReservation(1L);

        // then
        assertThat(memberReservation.size()).isEqualTo(reservationJpaRepository.findByMemberId(1L).size());
    }

    @Test
    @DisplayName("유저가 좌석을 예약할 수 있다.")
    void createReservation() {
        // given
        int beforeReservations = reservationJpaRepository.findByMemberId(1L).size();
        ReservationRequest reservationRequest = new ReservationRequest(1L, 1L, List.of(1, 2));
        // when
        Reservation reservation = reservationFacade.createReservation(reservationRequest);
        // then
        assertThat(reservationJpaRepository.findByMemberId(1L).size()).isEqualTo(beforeReservations + 1);
    }

    @Test
    @DisplayName("예약된 좌석을 예약할 수 없다.")
    void failedReservedCreateReservation() {
        // given
        ReservationRequest reservationRequest = new ReservationRequest(2L, 1L, List.of(14));

        // when, then
        assertThatThrownBy(() -> reservationFacade.createReservation(reservationRequest)).isInstanceOf(SeatInvalidException.class);
    }

    @Test
    @DisplayName("여러명의 유저가 동시에 좌석을 예약 시 한 유저만 좌석을 예약할 수 있다. ")
    void failedMultiCreateReservationWithTotalTiming() throws InterruptedException {
        // given
        ExecutorService executor = Executors.newFixedThreadPool(6);
        CountDownLatch latch = new CountDownLatch(6); // 요청 수와 동일하게 설정
        List<ReservationRequest> reservationRequests = List.of(
                new ReservationRequest(3L, 1L, List.of(1, 2, 4)), // 유저 3의 예약 요청
                new ReservationRequest(2L, 1L, List.of(4)), // 유저 2의 예약 요청
                new ReservationRequest(4L, 1L, List.of(4)), // 유저 4의 예약 요청
                new ReservationRequest(5L, 1L, List.of(4)), // 유저 5의 예약 요청
                new ReservationRequest(6L, 1L, List.of(4)), // 유저 6의 예약 요청
                new ReservationRequest(7L, 1L, List.of(4))  // 유저 7의 예약 요청
        );

        // 요청별 소요 시간 기록
        Map<Long, Long> requestTimes = new ConcurrentHashMap<>();

        // 전체 실행 시간 측정 시작
        long totalStartTime = System.nanoTime();
        AtomicInteger failCount = new AtomicInteger(0);
        // when
        reservationRequests.forEach(request -> {
            executor.submit(() -> {

                long startTime = System.nanoTime(); // 요청 시작 시간
                try {
                    reservationFacade.createReservation(request);
                }
                catch (Exception e) {
                    failCount.incrementAndGet();
                    log.info("Error : {} : {} ", e, e.getMessage());
                }
                long endTime = System.nanoTime(); // 요청 종료 시간
                requestTimes.put(request.getMemberId(), endTime - startTime); // 소요 시간 기록
                latch.countDown();
            });
        });

        latch.await(); // 모든 스레드가 완료될 때까지 대기
        executor.shutdown();

        // then
        long successfulReservations = reservationRequests.stream()
                .filter(request -> reservationService.findByMemberId(request.getMemberId()).size() == 1)
                .count();

        // 성공적으로 예약1, 실패 예약 5 확인
        assertThat(successfulReservations).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(5);

    }

}
