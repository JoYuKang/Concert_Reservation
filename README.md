# 콘서트 예약 프로젝트
이 시스템은 토큰 기반의 인증 및 대기열 관리 방식을 사용하여 유저가 공정하게 좌석을 예약할 수 있도록 하며, 실시간으로 예약 처리 및 결제 관리를 효율적으로 지원하는 것을 목표로 하고있습니다. 
시스템은 유저의 예약 및 결제 상태에 대한 관리, 잔고 충전 기능, 그리고 예약 실패 시 점유한 좌석 취소와 같은 비즈니스 로직을 내포하고 있습니다.    

- 토큰 기반 인증 및 대기열 관리: 유저의 토큰을 발급하여 토큰의 유효 기간 동안 예약, 좌석 조회 등 특정 기능을 제한적으로 제공. 이를 통해 동시성 문제를 해결하고, 불필요한 시스템 자원을 절약하며 공정한 처리를 보장합니다.
- 예약 상태 관리 및 자동 취소: 유저가 예약 후 결제를 진행하지 않으면, 결제 대기 시간이 지나면 자동으로 예약을 취소하고 해당 좌석을 오픈합니다. 예약 성공 후 결제까지의 시간을 관리하여 유저의 유효한 토큰을 처리합니다.
- 잔고 충전 및 결제: 토큰 없이도 잔고 충전 및 예약 조회, 결제가 가능하지만, 예약 후 결제 시에는 유효한 토큰을 필수로 사용하여 보안성을 강화하고 실시간 결제를 처리합니다.
- 유효한 토큰 만료 처리: 예약 후 결제 시 유저의 토큰은 만료됩니다. 유효한 토큰을 통한 예약 후 결제 상태까지의 흐름을 관리하여 유저의 데이터를 보호하고, 안정적인 시스템 운영을 보장합니다.
- 확장성 및 트랜잭션 관리: 시스템의 확장성을 고려하여 트랜잭션 관리를 통해 데이터 일관성을 유지하며, 예약 및 결제 중 발생할 수 있는 경합 상태를 방지하도록 설계되었습니다. 클린 아키텍처를 통해 유지보수와 확장성 또한 고려되었습니다.
  
## 서비스 설계
### 요구사항
- 유저 토큰 발급 API
- 예약 가능 날짜 / 좌석 API
- 좌석 예약 요청 API
- 잔액 충전 / 조회 API
- 결제 API
- 각 기능 및 제약사항에 대해 단위 테스트를 반드시 하나 이상 작성
- 다중 서버, 동시성 이슈를 고려
- 대기열 개념을 고려해 구현
### [Milestone](https://github.com/users/JoYuKang/projects/5)
### [Sequence Diagram](https://github.com/JoYuKang/Concert_Reservation/blob/docs/docs/%EC%8B%9C%ED%80%80%EC%8A%A4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.md)
### [ERD](https://github.com/JoYuKang/Concert_Reservation/blob/main/docs/ERD.md)
### [API 명세서](https://github.com/JoYuKang/Concert_Reservation/blob/docs/docs/API%20%EB%AA%85%EC%84%B8%EC%84%9C.md)
### [플로우차트](https://github.com/JoYuKang/Concert_Reservation/blob/docs/docs/%ED%94%8C%EB%A1%9C%EC%9A%B0%20%EC%B0%A8%ED%8A%B8.md)
## 페키지 구조
```
src/
├── support/
│   ├── common/ 
│          └── entity/                 # 공통 엔티티
│   ├── exception/                     # 예외 관리                  
│   ├── config/
│   │   ├── jpa/
│   │   ├── web/
│   ├── interceptor
│   ├──    http/                       # 유틸리티
│            └─ CommonResponse.java 
├── member/                   
│   ├── domain/
│   │   ├── Member.java.                 # Member 엔티티
│   │   ├── memberService.java.          # Member 서비스 
│   │   ├── MemberRepository.java.       # Member Interface   
│   ├── application
│   │   ├── facade
│   │   ├── Service
│   │   │   ├── memberService.java.          # Member 서비스 구현체
│   ├── interfaces/ 
│   │   ├── MemberController.java
│   │   ├── dto
│   │   │   ├── request
│   │   │   │   ├── MemberRequest.java
│   │   │   ├── response
│   │   │   │   ├── MemberResponse.java
└────── infrastructure/
        └── MemberJPARepository.java

```
## API Swagger

### 유저의 토큰 발급 API
<img width="1439" alt="image" src="https://github.com/user-attachments/assets/20cc7da3-05a7-43b5-a2bc-0ba29f65d28d" />

### 유저의 토큰 조회 API
<img width="1435" alt="image" src="https://github.com/user-attachments/assets/b692dee9-7e41-4b11-9308-1132bcb4f4c0" />

### 유저의 조회 API
![image](https://github.com/user-attachments/assets/1b422a75-f4d9-42f2-8101-f4b3d2f9701d)

### 유저의 충전 요청 API
![image](https://github.com/user-attachments/assets/a2d9a3f5-7319-48bb-a47c-546e5f02e83f)

### 유저의 예약 요청 API
![image](https://github.com/user-attachments/assets/0fe754f4-9ed5-4207-a2bf-554f782ad82a)

### 유저의 예약 결제 요청 API
![image](https://github.com/user-attachments/assets/bf359647-3607-4e0c-9e09-3aa62790b14a)

### 유저의 예약 내역 조회 API
![image](https://github.com/user-attachments/assets/bf301d58-2ac5-418e-ab90-aaf46a4b7d72)

### 콘서트 이름 조회 API
![image](https://github.com/user-attachments/assets/d6222461-fbdc-4eb6-840c-a834b08913d8)

### 콘서트 일자 조회 API
![image](https://github.com/user-attachments/assets/c2acf4ca-d89a-471d-bf20-1e28d7a1350e)

### 콘서트 좌석 조회 API
![image](https://github.com/user-attachments/assets/2b68a2f1-2a67-4026-807f-2c49d529d42e)


## 동시성 문제는 왜 발생하는가?

컴퓨터는 마치 여러 가지 일을 동시에 처리하는 것처럼 보이지만, 실제로는 한 번에 하나의 작업씩 순차적으로 처리하고 있습니다. 이 과정에서 동일한 데이터에 대해 여러 트랜잭션이 동시에 접근하거나 수정하려고 시도하면, 데이터의 정합성에 문제가 발생할 수 있습니다.

예를 들어, 두 트랜잭션이 동시에 같은 데이터를 읽고, 각각 수정한 뒤 저장하려고 하면, 한쪽의 수정 내용이 덮어씌워지거나 의도치 않은 값이 저장될 위험이 있습니다. 이러한 동시성 문제는 데이터 일관성을 위협하며 시스템의 안정성과 신뢰성을 저하시킬 수 있습니다.

이러한 이슈를 해결하기 위해 낙관적 락, 비관적 락, 레디스 등 다양한 방법으로 동시성을 해결하고 있습니다. 각각의 방법은 장단점이 있기 때문에 비즈니스 요구사항에 맞춰 데이터의 일관성을 유지하면서도 성능을 고려한 선택을 해야 합니다.

---

## 낙관적 락과 비관적 락 비교

| 특징           | 낙관적 락 (Optimistic Lock)                                                    | 비관적 락 (Pessimistic Lock)                                                  |
|----------------|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|
| 개념           | 데이터 읽을 때 락을 걸지 않으며, 변경 시 충돌 여부 확인 후 재시도 또는 실패 처리 | 데이터를 읽을 때 바로 락을 걸어 다른 트랜잭션이 데이터에 접근하지 못하게 함     |
| 충돌 처리 방식 | 충돌 발생 시 예외를 던지고, 재시도 로직이나 다른 처리 로직으로 문제를 해결         | 충돌 방지를 위해 트랜잭션 종료 시까지 데이터 락 유지                         |
| 사용 상황      | 충돌이 드물고 다중 사용자 환경에서 높은 동시성 보장이 필요한 경우               | 충돌 가능성이 높고 데이터 정합성이 중요한 경우                               |
| 성능           | 데이터 충돌이 적은 경우 성능 우수                                              | 락으로 인한 대기 시간 증가로 트랜잭션 처리량 감소                            |
| 락 방식        | DB 버전 필드(@Version)나 타임스탬프 활용                                       | DB의 S-Lock 또는 X-Lock 활용                                                |
| 장점           | 높은 동시성 처리 가능, 오버헤드 감소                                           | 데이터 정합성 보장                                                           |
| 단점           | 충돌이 빈번하면 재시도로 인한 성능 저하                                        | 대기 시간 증가, 데드락(Deadlock) 위험                                        |

---

## 비관적 락 (S-Lock, X-Lock) 비교

| 특징           | Shared Lock (S-Lock)                                              | Exclusive Lock (X-Lock)                                                     |
|----------------|-------------------------------------------------------------------|----------------------------------------------------------------------------|
| 개념           | 데이터를 읽기 작업에만 사용하도록 허용                              | 데이터를 읽기 및 수정 작업에 대해 독점적으로 사용                            |
| 허용되는 작업  | 읽기 작업 가능, 다른 트랜잭션도 동일 데이터 읽기 가능               | 읽기 및 쓰기 작업 가능, 다른 트랜잭션의 모든 작업 차단                       |
| 동시성 지원    | 읽기 작업 간 높은 동시성 지원                                      | 트랜잭션이 데이터 독점 접근, 동시성 제한                                     |
| 목적           | 읽기 작업 충돌 방지 및 데이터 변경 방지                             | 데이터 읽기/쓰기 충돌 방지 및 정합성 보장                                    |
| 교착 상태 가능성 | S-Lock과 X-Lock 혼합 사용 시 발생 가능                              | X-Lock 간 충돌로 Deadlock 발생 가능                                         |
| 성능           | 성능에 큰 영향 없음                                               | 동시성 낮아져 성능 저하 가능                                                |

---
## 콘서트에서 발생하는 동시성 이슈
콘서트에서 발생하는 대표적인 동시성 이슈는 콘서트의 좌석을 여러명의 유저가 동시에 에약하는 상황에서 주로 발생하여 다음 상황에서의 비관적 락과 낙관적 락의 적용 시 어떤 성능의 차이가 있는지 비교해 보겠습니다. 

## 콘서트 좌석 예약 로직 비관적 락 (X-Lock) 예제

```java
@Service
@RequiredArgsConstructor
public class ReservationFacade {

    private final ReservationService reservationService;
    private final MemberService memberService;
    private final ConcertService concertService;
    private final SeatService seatService;

    // 좌석 예약
    @Transactional
    public Reservation createReservation(ReservationRequest request) {
        // member 확인
        Member member = memberService.findById(request.getMemberId());

        // concert 확인
        Concert concert = concertService.getById(request.getConcertId());

        // 판매중인 Seat 확인 비관적 락 적용
        List<Seat> seats = seatService.searchSeatWithLock(request.getConcertId(), request.getSeatNumbers());

        // 예약 결제대기 저장
        Reservation reservation = new Reservation(member, concert, seats);

        return reservationService.save(reservation);
    }
}
```

### 좌석 조회 시 락 적용

```java
@Override
public List<Seat> searchSeatWithLock(Long concertId, List<Integer> seatNumbers) {
    List<Seat> selectedSeat = seatJpaRepository.findByConcertIdAndPositionWithLock(concertId, seatNumbers);

    for (Seat seat : selectedSeat) {
        if (seat.getStatus() == SeatStatus.SOLD_OUT) {
            throw new SeatInvalidException(ErrorMessages.SEAT_INVALID);
        }
        seat.reserve();
    }

    return seatJpaRepository.saveAll(selectedSeat);
}

@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT s FROM Seat s WHERE s.concert.id = :concertId AND s.seatNumber IN :positions")
List<Seat> findByConcertIdAndPositionWithLock(@Param("concertId") Long concertId, @Param("positions") List<Integer> positions);
```

---

## 콘서트 좌석 예약 로직 낙관적 락 (S-Lock) 예제

```java
@Service
@RequiredArgsConstructor
public class ReservationFacade {

    private final ReservationService reservationService;
    private final MemberService memberService;
    private final ConcertService concertService;
    private final SeatService seatService;

    // 좌석 예약
    @Transactional
    public Reservation createReservation(ReservationRequest request) {
        // member 확인
        Member member = memberService.findById(request.getMemberId());

        // concert 확인
        Concert concert = concertService.getById(request.getConcertId());

        // 판매중인 Seat 확인
        List<Seat> seats = seatService.searchSeat(request.getConcertId(), request.getSeatNumbers());

        // 예약 결제대기 저장
        Reservation reservation = new Reservation(member, concert, seats);

        return reservationService.save(reservation);
    }
}
```

### 데이터 업데이트에 @Version 추가 및 예외 처리

낙관적락은 데이터 업데이트 시 @Version을 확인하여 동시성을 관리합니다.
```java
public class Seat {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    @Column(name = "position")
    private Integer seatNumber;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private SeatStatus status; // AVAILABLE, SOLD_OUT

    @Version
    @Column(nullable = false)
    private Integer version = 0; // JPA가 자동으로 관리할 필드
}

```
낙관적락 좌석 확인 로직
```java
@Transactional
@Override
public List<Seat> searchSeat(Long concertId, List<Integer> seatNumbers) {
    try {
        // 요청된 좌석 가져오기
        List<Seat> selectedSeat = seatJpaRepository.findByConcertIdAndPosition(concertId, seatNumbers);
        // 좌석 유효성 확인
        for (Seat seat : selectedSeat) {
            if (seat.getStatus() == SeatStatus.SOLD_OUT) {
                throw new SeatInvalidException(ErrorMessages.SEAT_INVALID);
            }
            seat.reserve();
        }
        return seatJpaRepository.saveAll(selectedSeat);
    } catch (ObjectOptimisticLockingFailureException ex) {
        throw new SeatInvalidException(ErrorMessages.SEAT_NOT_FOUND);
    }
}
```

SeatInvalidException으로 좌석 예외를 관리하며, 좌석에 대한 오류 상세 내역을 SEAT_INVALID과 SEAT_NOT_FOUND의 문구를 통해 어떤 에러가 발생했는지 확인할 수 있습니다.

---

## 비관적 락 vs 낙관적 락 성능 비교

### 비관적 락 소요 시간
```
INFO 93109 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 2 request time: 86 ms
INFO 93109 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 3 request time: 92 ms
INFO 93109 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 4 request time: 86 ms
INFO 93109 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 5 request time: 75 ms
INFO 93109 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 6 request time: 103 ms
INFO 93109 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 7 request time: 103 ms
INFO 93109 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Total execution time: 104 ms
```

### 낙관적 락 소요 시간
```
INFO 8535 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 2 request time: 60 ms
INFO 8535 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 3 request time: 67 ms
INFO 8535 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 4 request time: 66 ms
INFO 8535 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 5 request time: 71 ms
INFO 8535 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 6 request time: 71 ms
INFO 8535 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Member 7 request time: 66 ms
INFO 8535 --- [main] k.h.b.s.r.a.f.ReservationFacadeTest : Total execution time: 72 ms
```

### 낙관적 락으로 변경한 이유

비관적 락에서 낙관적 락으로 변경한 주된 이유는 동시성 처리에서 성능을 개선하고 사용자 경험을 향상하기 위해서입니다. 기존 비관적 락 방식은 트랜잭션이 자원을 점유하는 동안 다른 트랜잭션이 대기해야 했기 때문에 대량의 동시 요청이 발생할 경우 성능 저하와 처리 속도 감소 문제가 발생했습니다. 반면, 낙관적 락은 자원 점유를 최소화하고 데이터 충돌이 실제로 발생했을 때만 이를 처리하는 방식으로 설계되어 성능을 향상시킬 수 있습니다.

낙관적 락 구현으로 첫 시도만을 허용하고 다른 동시성 이슈가 발생 시 더 이상 재시도를 진행하지 않고 예외를 클라이언트에게 전달합니다. 이런 방법으로 시스템 자원을 효율적으로 사용하면서 동시에 충돌 관리 로직을 간소화했습니다.

---

# Redis 캐싱 적용 전후 성능 비교

## 목적
Redis 캐싱을 적용하여 조회 성능을 개선하고, 캐싱의 효과를 측정합니다.

### 테스트 대상 메서드 (콘서트 메인 페이지 일자 조회)
```java
@Override
@Cacheable(value = "concerts", key = "'concert:' + #date")
public List<Concert> findByDate(LocalDate date) {
    return concertJpaRepository.findAllByClosestToToday(date);
}
```

``` java
// 주어진 날짜 이후의 콘서트를 조회하고, 해당 날과 가까운 날짜부터 멀어지는 순으로 정렬
@Query("SELECT c FROM Concert c WHERE c.concertDate >= :date ORDER BY c.concertDate ASC LIMIT 100")
List<Concert> findAllByClosestToToday(@Param("date") LocalDate date);
```

### 테스트 환경
- **데이터베이스 조회:** 데이터베이스에서 직접 데이터를 조회합니다.
- **Redis 캐시 조회:** Redis에 캐시된 데이터를 조회합니다.
- **총 샘플 수**: 10,000 (각 5,000개씩)
- **테스트 지표**: 평균 응답 시간, 최소/최대 응답 시간, 표준 편차, 에러율, 처리량, 네트워크 데이터 전송량

### 테스트 결과
| **항목**              | **Cache 조회**   | **DB 조회** |
|---------------------|--------------|-----------|
| **평균 응답 시간**        | 822 ms       | 1561 ms   |
| **최소 응답 시간**        | 3 ms         | 43 ms     |
| **최대 응답 시간**        | 2052 ms      | 2479 ms   |
| **표준 편차**           | 318.62 ms    | 344.61 ms |
| **에러율**             | 0.06%        | 0.00%     |
| **처리량**             | 981.0/sec    | 513.8/sec |
| **Received KB/sec** | 5102.45 KB   | 2673.20 KB |
| **Sent KB/sec**     | 179.03 KB    | 93.82 KB  |
| **평균 전송 바이트**       | 5326.3 bytes | 5328.0 bytes |

1. **성능**
    - Cache를 사용한 조회가 DB만을 사용한 조회보다 약 **47% 더 빠른** 평균 응답 시간을 기록했습니다.

2. **처리량**
    - Cache를 사용한 조회의 처리량(981.0/sec)이 DB만을 사용한 조회의 처리량(513.8/sec)보다 약 **90% 더 높았습니다.**

3. **네트워크 데이터 전송량**
    - Cache를 사용한 조회는 DB만을 사용한 조회보다 약 **91% 많은 Received KB/sec**를 기록했습니다.

4. **안정성**
    - DB만을 사용한 조회의 에러는 없었고 Cache를 사용한 조회에서 에러가 발생했지만 0.06%로 심각한 수준은 아니였습니다.

5. **응답 시간 변동성 (표준 편차)**
    - Cache를 사용한 조회와 DB만을 사용한 조회 모두 비슷한 수준의 변동성을 보였습니다.



## 캐싱을 적용한 이유
### 비즈니스 로직
**토큰 발급 요청 > 대기열 진입 > (폴링) > 대기열 통과 > 기본 콘서트 목록 조회 > 콘서트 좌석 조회 > 예약 > 결제 > 토큰 만료처리**

이 과정에서 **기본 콘서트 목록 조회**는 데이터 양이 많고, 여러 사용자에게 동일한 요청이 반복될 가능성이 큽니다.
- DB 부하를 줄이기 위해 **페이징 처리**를 적용하더라도 반복 요청이 많아 부하 가능성이 존재합니다.
- 콘서트 정보는 자주 변경되지 않아 동일한 데이터가 조회될 가능성이 높으므로, 캐싱이 적합합니다.

## 성능 개선
### 데이터베이스 부하 감소
- 데이터베이스는 디스크 기반 저장소로 데이터를 조회할 때 I/O 작업이 발생하지만 Redis는 메모리 기반 저장소로 데이터를 메모리에서 직접 조회하므로 속도가 데이터베이스에 비해 빠릅니다.

### 반복적인 조회 최적화
- 동일한 요청에 대해 DB 조회를 반복하지 않고 캐시에서 빠르게 데이터를 제공 가능

### 응답 시간 단축
- 캐시를 사용하면 데이터 조회 시간 단축


## 캐싱 전략

### [캐시 정리](https://yukang-laboratory.tistory.com/55)

### 1. 캐시 키 설계
- **키 형식:** `concert:2025-02-04`
- **설명:** 날짜를 기반으로 캐시 키를 생성하여, 특정 날짜의 콘서트 정보를 효율적으로 관리합니다.

### 2. TTL 설정
- **TTL:** 10분
- **설명:** 캐시된 데이터는 10분 후에 자동으로 삭제됩니다. 데이터의 최신성을 보장하고, 오래된 데이터가 캐시에 남아 있는 것을 방지합니다.

## 결론
### **캐시 활용의 중요성**
이번 성능 비교 결과 Redis 캐시 조회 속도가 DB 조회보다 훨씬 빠르며 캐시는 데이터를 메모리에 저장해 조회 성능을 크게 개선합니다. 트래픽이 많은 애플리케이션에서는 Redis를 활용해 데이터베이스 부하를 낮추고 응답 속도를 높이는 것이 효과적입니다. 자주 조회되는 데이터에 캐시를 적용하면 시스템 성능이 향상되며 DB 부하 없이 빠른 응답을 제공할 수 있다는 점을 배웠습니다.

## 향후 개선 방향
### 1. 캐시 적중률 모니터링
- 캐시 적중률을 모니터링하여 캐시의 효율성을 평가합니다.
- 적중률이 낮다면 **캐시 키 설계나 TTL을 조정**합니다.

### 2. 분산 캐시 적용
- 대규모 트래픽을 처리하기 위해 **Redis 클러스터를 구성하여 분산 캐시를 적용**합니다.

### 3. 캐시 무효화
- **전략:** 콘서트 정보가 변경될 때 캐시를 무효화합니다.
- **예시:** 새로운 콘서트가 추가되거나 기존 콘서트가 수정될 때, 해당 날짜의 캐시를 삭제합니다.


