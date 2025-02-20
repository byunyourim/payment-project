package payment.payment_project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import payment.payment_project.domain.Payment;
import payment.payment_project.enums.PaymentType;

@Slf4j
@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository repository;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder()
            .transactionId("M2NhNjczZTAtZjRjOS00")
            .encryptedCard("VafrJ3eKTF8oPJwibkMFpjpFCluevC/oSLC15k8PGw0mCv7rqGoCSg==")
            .installmentMonths("12")
            .transactionAmount(11000)
            .stringData(" 446PAYMENT   M2NhNjczZTAtZjRjOS001234567890123456    121125777    1100000000010000                    VafrJ3eKTF8oPJwibkMFpjpFCluevC/oSLC15k8PGw0mCv7rqGoCSg==                                                                                                                                                                                                                                                                                                   ")
            .vat(1000L)
            .type(PaymentType.PAYMENT)
            .build();

        repository.save(payment);
    }

    @Test
    @DisplayName("Payment - id로 조회 테스트")
    void testSaveAndFindById() {
        // when
        Optional<Payment> result = repository.findById(payment.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(payment.getId());
        assertThat(result.get().getTransactionId()).isEqualTo(payment.getTransactionId());
    }

    @Test
    @DisplayName("Payment - transaction id로 조회 테스트")
    void testSaveAndFindByTransactionId() {
        // when
        Optional<Payment> result = repository.findByTransactionId(payment.getTransactionId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(payment.getId());
        assertThat(result.get().getTransactionId()).isEqualTo(payment.getTransactionId());
        assertThat(result.get().getStringData()).isEqualTo(payment.getStringData());
    }
}