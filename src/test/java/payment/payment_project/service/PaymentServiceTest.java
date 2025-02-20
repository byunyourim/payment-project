package payment.payment_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import payment.payment_project.controller.response.CardPaymentResponse;
import payment.payment_project.domain.Payment;
import payment.payment_project.enums.PaymentType;
import payment.payment_project.repository.PaymentRepository;
import payment.payment_project.service.dto.PaymentDto;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("정상 결제 테스트")
    void testCreatePayment() {
        //given
        PaymentDto paymentDto = PaymentDto.builder()
            .cardNumber("1234567890123456")
            .expiryDate("1225")
            .cvc("123")
            .installmentMonths("00")
            .transactionAmount(11000L)
            .vat(null) // 자동 계산 로직 검증용
            .type(PaymentType.PAYMENT)
            .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(null);

        // when
        CardPaymentResponse response = paymentService.createPayment(paymentDto);

        // then
        assertNotNull(response);
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getStringData());

        long expectedVat = paymentDto.getTransactionAmount() / 11;
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());

        Payment savedPayment = paymentCaptor.getValue();
        assertEquals(expectedVat, savedPayment.getVat()); // VAT 자동 계산 확인
        assertEquals(PaymentType.PAYMENT, savedPayment.getType()); // 타입 확인
        assertEquals(paymentDto.getInstallmentMonths(), savedPayment.getInstallmentMonths()); // 할부 개월 확인
    }

}