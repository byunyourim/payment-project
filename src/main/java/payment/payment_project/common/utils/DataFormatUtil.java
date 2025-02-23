package payment.payment_project.common.utils;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import payment.payment_project.common.constants.PaymentConstants;

/**
 * Data Format Util
 * 데이터 포맷과 관련된 작업 클래스
 *
 * @author winnie
 * @version 1.0
 * @since 2025-02-20
 */

@Slf4j
public class DataFormatUtil {

    public static String stringToLongFormat(String str, int length, String type) {
        Long value = Long.parseLong(str);
        return formatNumber(value, length, type);
    }

    /**
     * 숫자 format 매서드
     * - basic -> 우측 정렬, 빈 칸으로 채움
     * - zero  -> 우측 정렬, 0 으로 채움
     * - left  -> 좌측 정렬, 빈 칸으로 채움
     */
    public static String formatNumber(Long number, int length, String type) {
        switch (type) {
            case PaymentConstants.NUMBER_BASIC:
                return String.format("%" + length + "d", number);
            case PaymentConstants.NUMBER_ZERO:
                return String.format("%0" + length + "d", number);
            case PaymentConstants.NUMBER_LEFT:
                return String.format("%-" + length + "d", number);
            default:
                throw new IllegalArgumentException("Invalid number format type: " + type);
        }
    }

    /**
     * 문자 format 메서드
     * - 좌측 정렬, 빈칸으로 채움
     */
    public static String formatString(String str, int length) {
        return String.format("%-" + length + "s", (str == null) ? "" : str);
    }

    public static int getByteLength(String str) {
        return str == null ? 0 : str.getBytes(StandardCharsets.UTF_8).length;
    }

    /**
     * 카드 정보 암호화 메서드
     */
    public static String encryptCardInfo(String cardNumber, String expiryDate, String cvc) {
        if (cardNumber == null) {
            throw new IllegalArgumentException("cardNumber cannot be null");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("expiryDate cannot be null");
        }
        if (cvc == null) {
            throw new IllegalArgumentException("cvc cannot be null");
        }

        String fullData = cardNumber.concat("|").concat(cardNumber)
            .concat("|").concat(expiryDate)
            .concat("|").concat(cvc);

        try {
            return EncryptUtil.encrypt(fullData);

        } catch (Exception e) {
            throw new RuntimeException("카드 정보 암호화 실패!", e);
        }
    }

    /**
     * 부가가치세 계산 메서드
     */
    public static Long setVat(Long vat, Long amount) {
        return (vat == null) ? amount / 11 : vat;
    }
}
