# payment-project
##### 간단한 결제, 취소 요청을 받아 통신하는 결제 시스템  
- embeded DB H2 사용
- API를 개발 및 단위 테스트

---
## Tech 
- **Language:** Java 17
- **Framework:** Spring Boot 3.4.2
- **Database:** H2 (Embedded)
- **ORM:** JPA (Hibernate)
- **Build Tool:** Gradle
- **Testing:** JUnit 5, Mockito


## 구현 할 API
- 결제 API
- 데이터 조회 API
- 결제 전체 취소 API
- 결제 부분 취소 API
---

## API 명세



---

## 테이블 설계 

### payment 테이블

| No  | Column Name        | Type                | Not Null | Default | Extra            | Description                     |
|-----|--------------------|---------------------|----------|---------|------------------|---------------------------------|
| 1   | id                | BIGINT              | ✅        |         | AUTO_INCREMENT   | PK, 자동 증가 ID               |
| 2   | transaction_id     | VARCHAR(20)         | ✅        |         | UNIQUE           | 관리번호 (20자리)              |
| 3   | encrypted_card     | VARCHAR(255)        | ✅        |         |                  | 암호화된 카드 정보             |
| 4   | installment_months | VARCHAR(255)        | ✅        |         |                  | 할부 개월 수 (0: 일시불)       |
| 5   | transaction_amount | BIGINT              | ✅        |         |                  | 결제 금액                      |
| 6   | type              | ENUM('PAYMENT', 'CANCEL') | ✅ |         |                  | 결제 구분 (결제/취소/부분취소) |
| 7   | vat               | BIGINT              |          | NULL    |                  | 부가가치세                     |
| 8   | string_data       | TEXT                |          | NULL    |                  | 카드사로 보내는 데이터         |
| 9   | created_at        | TIMESTAMP           | ✅        | NOW()   |                  | 결제 시간                      |

### payment_cancel 테이블
| No  | Column Name         | Type                | Not Null | Default | Extra          | Description                          |
|-----|---------------------|---------------------|----------|---------|--------------|--------------------------------------|
| 1   | id                 | BIGINT              | ✅        |         | AUTO_INCREMENT | PK, 자동 증가 ID                     |
| 2   | cancel_transaction_id | VARCHAR(20)    | ✅        |         | UNIQUE       | 취소 관리번호 (20자리)               |
| 3   | cancel_amount       | BIGINT              | ✅        |         |              | 취소 금액                            |
| 4   | cancel_vat          | BIGINT              |          | NULL    |              | 취소 부가가치세                      |
| 5   | type               | ENUM('FULL', 'PART') | ✅        |         |              | 취소 타입 (전체취소/부분취소)        |
| 6   | string_data        | TEXT                | ✅        |         |              | 카드사에 보낸 취소 데이터            |
| 7   | result             | VARCHAR(255)        |          | NULL    |              | 응답 데이터                          |
| 8   | status             | VARCHAR(255)        |          | NULL    |              | 취소 상태                            |
| 9   | canceled_at        | TIMESTAMP           | ✅        | NOW()   |              | 취소 시간                            |
| 10  | transaction_id     | VARCHAR(20)         | ✅        |         | FK to `payment(transaction_id)` | 결제 관리번호 |

---

## 프로젝트 폴더 구조
```
payment-project
├── common           # 공통 유틸 및 상수 관리
│   ├── constants
│   ├── utils
├── controller       # API 요청 처리
│   ├── request
│   ├── response
├── domain           # JPA Entity
├── enums            # Enum 
├── repository       # JPA Repository
├── service          # 비즈니스 로직
│   ├── dto
├── PaymentProjectApplication.java
```

---
## 빌드 및 실행 방법

---
## API 요청/응답 예시


