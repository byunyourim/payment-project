package payment.payment_project.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import payment.payment_project.common.utils.PaymentDataUtil;
import payment.payment_project.common.utils.valid.BusinessValidatable;
import payment.payment_project.controller.request.CreateCardPaymentRequest;
import payment.payment_project.domain.Payment;
import payment.payment_project.enums.PaymentType;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto implements BusinessValidatable {

    private String cardNumber;

    private String expiryDate;

    private String cvc;

    private String installmentMonths;

    private Long transactionAmount;

    private Long cancelAmount;

    private Long vat;

    private String transactionId;

    private String cancelTransactionId;

    private String encryptCardInfo;

    private String etc;

    private String stringData;

    private PaymentType type;

    /**
     * CreateCardPaymentRequest -> PaymentDto (결제)
     */
    public static PaymentDto from (CreateCardPaymentRequest request) {
        return PaymentDto.builder()
            .cardNumber(request.getCardNumber())
            .expiryDate(request.getExpiryDate())
            .cvc(request.getCvc())
            .installmentMonths(request.getInstallmentMonths())
            .transactionAmount(request.getTransactionAmount())
            .vat(request.getVat())
            .type(PaymentType.PAYMENT)
            .build();
    }

    public static Payment toEntity(PaymentDto dto, String transactionId, String stringData) {
        return Payment.builder()
            .transactionId(transactionId)
            .encryptedCard(PaymentDataUtil
                .encryptCardInfo(dto.getCardNumber(), dto.getExpiryDate(), dto.getCvc()))
            .installmentMonths(dto.getInstallmentMonths())
            .transactionAmount(dto.getTransactionAmount())
            .vat(PaymentDataUtil.setVat(dto.getVat(), dto.getTransactionAmount()))
            .stringData(stringData)
            .type(dto.getType())
            .build();
    }

    @Override
    public void validateBusinessRules() {

    }

}
