package payment.payment_project.common.utils.valid;

/**
 * Validation Util
 * dto 비즈니스 로직을 검증하는 유틸 클래스
 *
 * @author  winnie
 * @version 1.0
 * @since   2025-02-20
 */
public class ValidationUtil {

    public static <T extends BusinessValidatable> void checkBusinessRules(T dto) {
        dto.validateBusinessRules();
    }
}
