package payment.payment_project.controller.response;

import lombok.Builder;
import lombok.Getter;
import payment.payment_project.enums.PaymentType;

@Getter
@Builder
public class RetrievePaymentResponse {

    private String transactionId;

    private String cardNumber;

    private String expiryDate;

    private String cvc;

    private PaymentType type;

    private Long transactionAmount;

    private Long canceelAmount;

    private Long vat;
}
