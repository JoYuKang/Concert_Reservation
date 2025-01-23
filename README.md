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

```java
@Retryable(
    retryFor = OptimisticLockException.class,
    maxAttempts = 2,
    backoff = @Backoff(delay = 100)
)
@Override
public List<Seat> searchSeat(Long concertId, List<Integer> seatNumbers) {
    if (!isRetryable) {
        throw new OptimisticLockException("Retry attempts exceeded.");
    }

    try {
        List<Seat> selectedSeat = seatJpaRepository.findByConcertIdAndPosition(concertId, seatNumbers);

        for (Seat seat : selectedSeat) {
            if (seat.getStatus() == SeatStatus.SOLD_OUT) {
                throw new SeatInvalidException(ErrorMessages.SEAT_INVALID);
            }
            seat.reserve();
        }

        List<Seat> savedSeats = seatJpaRepository.saveAll(selectedSeat);
        seatJpaRepository.flush();
        return savedSeats;
    } catch (ObjectOptimisticLockingFailureException ex) {
        if (isRetryable) {
            isRetryable = false;
            throw ex;
        } else {
            throw new OptimisticLockException("예약에 실패했습니다.");
        }
    }
}
```

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

---

### 낙관적 락으로 변경한 이유

낙관적 락 방식으로 변경한 주된 이유는 동시성 처리 시 성능에 좀 더 우선순위를 두어 사용자 경험을 향상시키기 위함입니다. 비관적 락은 트랜잭션 간 대기가 발생하여 대량 요청 시 성능 저하가 우려되는 반면, 낙관적 락은 데이터 충돌이 실제로 발생했을 때만 이를 처리하므로 성능 향상을 기대할 수 있습니다.

낙관적 락 구현에서는 첫 번째 트랜잭션 실패 시 100ms 대기 후 최대 1회 재시도를 허용하며, 반복 충돌 시 신속히 예외를 반환하여 사용자 대기 시간을 최소화했습니다. 이를 통해 시스템 자원 사용을 최적화하고 성능을 유지하면서 동시성 이슈를 효과적으로 관리했습니다.




