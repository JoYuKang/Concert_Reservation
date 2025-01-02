# ERD 설계

```mermaid
erDiagram
    MEMBER {
        Long id PK
        Varchar name
        Int balance
    }
    TOKEN {
        UUID id PK
        Enum status
        Timestamp created_at
        Timestamp expired_at
    }
    CONCERT {
        Long id PK
        Varchar name
        Date date
    }
    RESERVATION {
        Long id PK
        Long user_id FK
        Long concert_id FK
        Long seat_id FK
        Int total_amount
        Enum status
        Timestamp created_at
    }
    SEAT {
        Long id PK
        Long concert_id FK
        Int position
        Int amount
    }
    BALANCE_HISTORY {
        Long id PK
        Long user_id FK
        Int amount
        Timestamp created_at
    }

    MEMBER ||--o| RESERVATION : has
    CONCERT ||--o| RESERVATION : hosts
    SEAT ||--o| RESERVATION : is_reserved
    MEMBER ||--o| BALANCE_HISTORY : has