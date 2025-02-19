# payment-project
a simple payment project

간단한 결제, 취소 요청을 받아 통신하는 결제 시스템  

---
### Payment Table Structure

| No | Column Name         | Type                             | Not Null | Default      | Extra                  | Description                        |
|----|---------------------|---------------------------------|----------|-------------|------------------------|------------------------------------|
| 1  | `payment_id`       | `BIGINT`                        | ✅       | `AUTO_INCREMENT` | `PRIMARY KEY`         | 결제 PK (자동 증가) |
| 2  | `transaction_id`   | `VARCHAR(20)`                   | ✅       | -           | `UNIQUE`               | 고유 관리번호 (Unique ID) |
| 3  | `card_number`      | `VARCHAR(300)`                  | ✅       | -           | 암호화 저장             | 암호화된 카드 번호 |
| 4  | `cvc`              | `VARCHAR(300)`                  | ✅       | -           | 암호화 저장             | 암호화된 CVC |
| 5  | `expiry_date`      | `VARCHAR(300)`                  | ✅       | -           | 암호화 저장             | 암호화된 유효기간 |
| 6  | `transaction_amount` | `BIGINT`                      | ✅       | -           | `CHECK (100 ≤ amount ≤ 10억)` | 결제 금액 (100원 이상, 10억 원 이하) |
| 7  | `cancel_amount`    | `BIGINT`                        | ❌       | `0`         | -                      | 취소 금액 (기본값 0) |
| 8  | `payment_type`     | `ENUM('PAYMENT', 'CANCEL')`     | ✅       | `'PAYMENT'` | -                      | 결제/취소 구분 |
| 9  | `vat`              | `BIGINT`                        | ❌       | `0`         | -                      | 부가가치세 (선택 사항) |





