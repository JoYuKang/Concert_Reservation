# 콘서트 예약 프로젝트
이 프로젝트는 토큰 기반 대기열 관리 시스템을 활용하여 동시성을 제어하고 실시간 좌석 예약을 효율적으로 처리하는 것을 목표로 설계되었습니다.    
좌석 예약 과정에서 트랜잭션 관리를 통해 데이터의 일관성을 유지하고 확장성을 고려한 클린 아키텍처 패키지 구조를 따르고 있습니다.
- 토큰 기반 대기열 관리: 다수의 사용자가 동시에 요청을 보내는 환경에서도 공정한 처리를 보장, 불필요한 시스템 부하를 최소화
- 트랜잭션 관리 및 데이터 일관성: 좌석 예약 중 발생할 수 있는 데이터 경합 상태를 방지화되도록 설계
- 확장성: 향후 기능 확장 및 유지보수가 용이하도록 DIP를 지키는 패키지 구성
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
### [ERD](https://github.com/JoYuKang/Concert_Reservation/blob/docs/docs/ERD.md)
### [API 명세서](https://github.com/JoYuKang/Concert_Reservation/blob/docs/docs/API%20%EB%AA%85%EC%84%B8%EC%84%9C.md)
## 페키지 구조
```
src/
├── support/
│   ├── common/ 
│          └── entity/                 # 공통 엔티티
│   ├── exception/                     # 예외 관리
│   ├──    http/                       # 유틸리티
│            └─ CommonResponse.java 
├── member/                   
│   ├── domain/
│   │   ├── Member.java.                 # Member 엔티티
│   │   ├── memberService.java.          # Member 서비스 구현체
│   │   ├── MemberRepository.java.       # Member Interface   
│   ├── application
│   │   ├── facade
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

