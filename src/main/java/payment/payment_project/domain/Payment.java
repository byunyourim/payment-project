package payment.payment_project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import payment.payment_project.enums.PaymentType;

@Entity
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 16)
    private String cardNumber;

    @Column(nullable = false, length = 14)
    private String expirationDate;

    @Column(nullable = false, length = 3)
    private String cvc;

    @Column(nullable = false)
    private String installmentMonths;

    private long price;

    private long cancelPrice;

    private String vat;     // optional

    private String stringData;

    private PaymentType type;

}
