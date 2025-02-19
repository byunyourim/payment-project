package payment.payment_project.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateCardPaymentRequest {

    @NotBlank(message = "카드 번호는 필수 입력값입니다.")
    @Pattern(regexp = "^[0-9]{10,16}$", message = "카드 번호는 10~16자리 숫자입니다.")
    private String cardNumber;

    @NotBlank(message = "유효기간은 필수 입력값입니다.")
    @Pattern(regexp = "^(0[1-9]|1[0-2])([0-9]{2})$", message = "유효기간은 MMYY 형식의 4자리 숫자여야 합니다. (예: 0327)")
    private String expiryDate;

    @NotBlank(message = "CVC는 필수 입력값입니다.")
    @Pattern(regexp = "^[0-9]{3}$", message = "CVC 값은 3자리 숫자여야 합니다.")
    private String cvc;

    @NotBlank(message = "할부개월 수는 필수 입력값입니다.")
    @Pattern(regexp = "^(0|[1-9]|1[0-2])$", message = "할부 개월 수는 0-12 값이어야 합니다. (0: 일시불)")
    private String installmentMonths;

    @Min(value = 100, message = "결제 금액은 최소 100원 이상이어야 합니다.")
    @Max(value = 1_000_000_000, message = "결제 금액은 최대 10억 원까지 가능합니다.")
    private Long transactionAmount;

    @Min(value = 0, message = "부가가치세는 0원 이상이어야 합니다.")
    private Long vat;

}
