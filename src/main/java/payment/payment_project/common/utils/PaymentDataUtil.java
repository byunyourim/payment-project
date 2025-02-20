package payment.payment_project.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import payment.payment_project.common.constants.PaymentConstants;
import payment.payment_project.service.dto.PaymentDto;

/**
 * PaymentData Util
 * 결제와 관련된 작업 클래스
 *
 * @author winnie
 * @version 1.0
 * @since 2025-02-20
 */

@Slf4j
public class PaymentDataUtil {

    /**
     * 공통 헤더 생성 메서드
     */
    public static String createCommonHeader(String data, String dataType, String transactionId) {
        int dataLength = getByteLength(data) + getByteLength(dataType) + getByteLength(transactionId);

        String stringHeader = new StringBuilder()
            .append(formatNumber(Long.valueOf((long) dataLength), 4, PaymentConstants.NUMBER_BASIC))
            .append(formatString(dataType, 10))
            .append(formatString(transactionId, 20))
            .toString();
        
        return stringHeader;
    }

    /**
     * 데이터 생성 메서드
     */
    public static String createData(PaymentDto paymentDto) {
        StringBuilder builder = new StringBuilder();
        builder.append(stringToLongFormat(paymentDto.getCardNumber(), 20, PaymentConstants.NUMBER_LEFT))
            .append(stringToLongFormat(paymentDto.getInstallmentMonths(), 2, PaymentConstants.NUMBER_ZERO))
            .append(stringToLongFormat(paymentDto.getExpiryDate(), 4, PaymentConstants.NUMBER_LEFT))
            .append(stringToLongFormat(paymentDto.getCvc(), 3, PaymentConstants.NUMBER_LEFT))
            .append(formatNumber(paymentDto.getTransactionAmount(), 10, PaymentConstants.NUMBER_BASIC))
            .append(formatNumber(paymentDto.getVat(), 10, PaymentConstants.NUMBER_ZERO))
            .append(formatString(paymentDto.getTransactionId(), 20))
            .append(formatString(paymentDto.getEncryptCardInfo(), 300))
            .append(formatString(paymentDto.getEtc(), 47));

        return builder.toString();
    }

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

    private static int getByteLength(String str) {
        return str == null ? 0 : str.getBytes(StandardCharsets.UTF_8).length;
    }

    /**
     * 카드 사로 전송할 stringData 생성 메서드
     */
    public static String generateStringData(String commonHeader, String data) {
        return commonHeader.concat(data);
    }

    /**
     * 카드 정보 암호화 메서드
     */
    public static String encryptCardInfo(String cardNumber, String expiryDate, String cvc) {
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
     * 관리 번호 생성 메서드
     */
    public static String generateTransactionId() {
        return UUID.randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 20);
    }

    /**
     * 부가가치세 계산 메서드
     */
    public static Long setVat(Long vat, Long amount) {
        return (vat == null) ? amount / 11 : vat;
    }
}
