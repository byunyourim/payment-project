package payment.payment_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import payment.payment_project.enums.PaymentType;

@Entity
@Table(name = "payment")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String transactionId;

    /** 암호화된 카드 정보 */
    @JsonIgnore
    @Column(nullable = false)
    private String encryptedCard;

    /** 할부 개월 수 (0: 일시불) */
    @Column(nullable = false)
    private String installmentMonths;

    /** 결제 금액 */
    @Column(nullable = false)
    private long transactionAmount;

    /** 결제구분 [결제/취소/부분취소] */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    /** 부가가치세 */
    private Long vat;

    /** 카드사로 보내는 데이터 */
    @Lob
    @Column(length = 450)
    private String stringData;

    /** 결제 시간 */
    @CreatedDate
    private LocalDateTime createdAt;


    // TODO: 추후 카드 결제 이외의 결제 수단 지원 예정
}
