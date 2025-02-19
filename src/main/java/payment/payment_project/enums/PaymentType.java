package payment.payment_project.enums;

public enum PaymentType {
    PAYMENT("결제"),
    CANCEL("결제 취소"),
    CANCEL_PART("부분 취소");

    private String code;

    PaymentType(String code) {
        this.code = code;
    }
}
