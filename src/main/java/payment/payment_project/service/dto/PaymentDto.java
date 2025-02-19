package payment.payment_project.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import payment.payment_project.controller.request.CreateCardPaymentRequest;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private String cardNumber;

    private String expiryDate;

    private String cvc;

    private String installmentMonths;

    private long transactionAmount;

    private long cancelAmount;

    private long vat;

    private String transactionId;

    private String cancelTransactionId;

    private String encryptCardInfo;

    private String etc;


    public static PaymentDto from (CreateCardPaymentRequest request) {
        return PaymentDto.builder()
            .cardNumber(request.getCardNumber())
            .expiryDate(request.getExpiryDate())
            .cvc(request.getCvc())
            .installmentMonths(request.getInstallmentMonths())
            .transactionAmount(request.getTransactionAmount())
            .vat(request.getVat())
            .build();
    }

}
