package payment.payment_project.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CardPaymentResponse {

    private String transactionId;

    private String sentToCardCompany;

}
