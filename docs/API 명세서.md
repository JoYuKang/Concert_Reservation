# API 명세서

## API 목차
- [유저의 예약 내역 조회](#유저의-예약-내역-조회)
- [유저의 잔고 조회](#유저의-잔고-조회)
- [유저의 충전 요청](#유저의-충전-요청)
- [콘서트 조회](#콘서트-조회)
- [특정 콘서트의 좌석 조회](#특정-콘서트의-좌석-조회)
- [좌석 예약 요청](#좌석-예약-요청)
- [예약한 좌석의 결제 요청](#예약한-좌석의-결제-요청)

## 개요
### 향 후 Id값을 Headers 넣어서 사용하도록 수정 예정
### 유저의 예약 내역 조회 API
- **URL:** `/members/{id}/reservations`
- **Method:** `GET`
- **URL Params:**
    - `id=[integer]` (required) 유저 고유값 
- **Success Response:**
    - **Code:** 200 OK
    - **Content:**
    ```json
    [
      {
        "concert_name": "Winter Concert",
        "seat_number": 15,
        "status": "Confirmed",
        "created_at": "2025-01-01T10:00:00Z"
      },
      {
        "concert_name": "Spring Concert",
        "seat_number": 20,
        "status": "Pending",
        "created_at": "2025-02-01T12:00:00Z"
      }
    ]
    ```
- **Error Response:**
    - **Code:** 400 Bad Request
    - **Content:**
    ```json
    {
      "error": "유저를 찾을 수 없습니다."
    }
    ```

### 유저의 잔고 조회
- **URL:** `/members/{id}/balance`
- **Method:** `GET`
- **URL Params:**
    - `id=[integer]` (required) 유저 고유값
- **Success Response:**
    - **Code:** 200 OK
    - **Content:**
    ```json
    {
      "balance": 1000
    }
    ```
- **Error Response:**
    - **Code:** 400 Bad Request
    - **Content:**
    ```json
    {
      "error": "유저를 찾을 수 없습니다."
    }
    ```

### 유저의 충전 요청
- **URL:** `/members/{id}/balance`
- **Method:** `POST`
- **URL Params:**
    - `id=[integer]` (required) 유저 고유값
- **Request Body:**
    - **Content:**
    ```json
    {
      "balance": 1000
    }
    ```
- **Success Response:**
    - **Code:** 201 OK
    - **Content:**
    ```json
    {
      "balance": 1500
    }
    ```
- **Error Response:**
    - **Code:** 400 Bad Request
    - **Content:**
    ```json
    {
      "error": "유저를 찾을 수 없습니다."
    },
    {
      "error": "0원 이하의 금액을 충전할 수 없습니다."
    }
    ```

### 콘서트 조회
- **URL:** `/concerts`
- **Method:** `GET`
- **URL Params:**
    - None
- **Success Response:**
    - **Code:** 200 OK
    - **Content:**
    ```json
    [
      {
        "name": "Winter Concert",
        "date": "2025-01-01T10:00:00Z"
      },
      {
        "name": "Spring Concert",
        "date": "2025-03-01T18:00:00Z"
      }
    ]
    ```
### 특정 콘서트의 좌석 조회
- **URL:** `/concerts/{concertId}/seats`
- **Method:** `GET`
- **URL Params:**
    - `concertId=[integer]` (required) 콘서트 고유값
- **Success Response:**
    - **Code:** 200 OK
    - **Content:**
    ```json
    [
      {
        "concert_name": "Winter Concert",
        "position": 1,
        "amount": 50000
      },
      {
        "concert_name": "Winter Concert",
        "position": 2,
        "amount": 50000
      }
    ]
    ```
- **Error Response:**
    - **Code:** 400 Bad Request
    - **Content:**
    ```json
    {
      "error": "콘서트를 찾을 수 없습니다."
    }
    ```

### 좌석 예약 요청
- **URL:** `/concerts/{concertId}/seats/{seatId}/reservations`
- **Method:** `POST`
- **URL Params:**
    - `concertId=[integer]` (required) 콘서트 고유값
    - `seatId=[integer]` (required) 좌석 고유값
- **Request Body:**
    - **Content:**
    ```json
    {
      "userId": 1
    }
    ```
- **Success Response:**
    - **Code:** 201 OK
    - **Content:**
    ```json
    {
      "concert_name": "Winter Concert",
      "seat_number": 15,
      "status": "Pending",
      "created_at": "2025-01-01T10:00:00Z"
    }
    ```
- **Error Response:**
    - **Code:** 400 Bad Request
    - **Content:**
    ```json
    {
      "error": "콘서트가 유효하지 않습니다."
    },
    {
      "error": "좌석이 유효하지 않습니다."
    },
    {
      "error": "예약할 수 있는 좌석이 아닙니다."
    }
    ```

### 예약한 좌석의 결제 요청
- **URL:** `/reservations/{reservationId}/payments`
- **Method:** `POST`
- **URL Params:**
    - `reservationId=[integer]` (required) 예약 고유값
- **Request Body:**
    - **Content:**
    ```json
    {
      "userId": 1
    }
    ```
- **Success Response:**
    - **Code:** 201 OK
    - **Content:**
    ```json
    {
      "concert_name": "Winter Concert",
      "amount": 50000
      "seat_number": 15,
      "status": "Complete",
    }
    ```
- **Error Response:**
    - **Code:** 400 Bad Request
    - **Content:**
    ```json
    {
      "error": "잔액이 충분하지 않습니다."
    },
    {
      "error": "결제가 가능한 시간이 지났습니다."
    }
    ```
