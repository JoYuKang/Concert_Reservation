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




