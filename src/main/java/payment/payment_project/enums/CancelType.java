package payment.payment_project.enums;


public enum CancelType {
    FULL("전체취소"),
    PART("부분취소");

    private String code;

    CancelType(String code) {
        this.code = code;
    }
}
