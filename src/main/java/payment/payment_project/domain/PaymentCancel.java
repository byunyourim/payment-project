package payment.payment_project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import payment.payment_project.enums.CancelType;

@Entity
@Table(name = "payment_cancel")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 취소 관리번호 (Unique ID, 20자리) */
    @Column(nullable = false, unique = true, length = 20)
    private String cancelTransactionId;

    /** 취소 금액 */
    @Column(nullable = false)
    private Long cancelAmount;

    /** 취소 부가세 */
    private Long cancelVat;

    /** 결제 취소 타입 [전체취소/부분취소] */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CancelType type;

    /** 카드사에 보낸 취소 데이터 */
    @Lob
    @Column(nullable = false)
    private String stringData;

    /** 응답 데이터 */
    private String result;

    /** 취소 금액 */
    private String status;

    /** 취소 시간 */
    @CreatedDate
    private LocalDateTime canceledAt;

    /** 결제 관리번호 */
    @OneToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "transactionId")
    private Payment payment;


}
