package payment.payment_project.controller.request;


import lombok.Getter;

@Getter
public class CardPaymentRequest {

    private String cardNo;

    private String expirationDate;

    private String cvc;

    private String installmentMonths;

    private long price;

    private String vat;
}
