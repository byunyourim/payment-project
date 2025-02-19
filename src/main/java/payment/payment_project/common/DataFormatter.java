package payment.payment_project.common;

import java.nio.charset.StandardCharsets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import payment.payment_project.common.constants.Constatns;
import payment.payment_project.service.dto.PaymentDto;

@Getter
@RequiredArgsConstructor
public class DataFormatter {

    public static String createCommonHeader(String data, String dataType, String transactionId) {
        int dataLength = getByteLength(data) + getByteLength(dataType) + getByteLength(transactionId);

        String stringHeader = new StringBuilder()
            .append(formatNumber(Long.valueOf((long) dataLength), 4, Constatns.NUMBER_BASIC))
            .append(formatString(dataType, 10))
            .append(formatString(transactionId, 20))
            .toString();
        
        return stringHeader;
    }

    public static String createData(PaymentDto paymentDto) {
        StringBuilder builder = new StringBuilder();
        builder.append(stringToLongFormat(paymentDto.getCardNumber(), 20, Constatns.NUMBER_LEFT))
            .append(stringToLongFormat(paymentDto.getInstallmentMonths(), 2, Constatns.NUMBER_ZERO))
            .append(stringToLongFormat(paymentDto.getExpiryDate(), 4, Constatns.NUMBER_LEFT))
            .append(stringToLongFormat(paymentDto.getCvc(), 3, Constatns.NUMBER_LEFT))
            .append(formatNumber(paymentDto.getTransactionAmount(), 10, Constatns.NUMBER_BASIC))
            .append(formatNumber(paymentDto.getVat(), 10, Constatns.NUMBER_ZERO))
            .append(formatString(paymentDto.getTransactionId(), 20))
            .append(formatString(paymentDto.getEncryptCardInfo(), 300))
            .append(formatString(paymentDto.getEtc(), 47));

        return builder.toString();
    }

    public static String stringToLongFormat(String str, int length, String type) {
        Long value = Long.parseLong(str);

        return formatNumber(value, length, type);
    }

    public static String formatNumber(Long number, int length, String type) {
        switch (type) {
            case Constatns.NUMBER_BASIC:
                return String.format("%" + length + "d", number);
            case Constatns.NUMBER_ZERO:
                return String.format("%0" + length + "d", number);
            case Constatns.NUMBER_LEFT:
                return String.format("%-" + length + "d", number);
            default:
                throw new IllegalArgumentException("Invalid number format type: " + type);
        }
    }

    private static int getByteLength(String str) {
        return str == null ? 0 : str.getBytes(StandardCharsets.UTF_8).length;
    }

    public static String formatString(String str, int length) {
        return String.format("%-" + length + "s", (str == null) ? "" : str);
    }

    public static String generateStringData(String commonHeader, String data) {
        return commonHeader.concat(data);
    }
}
