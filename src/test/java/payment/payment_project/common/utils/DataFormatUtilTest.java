package payment.payment_project.common.utils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payment.payment_project.common.constants.PaymentConstants;

class DataFormatUtilTest {

    @Test
    @DisplayName("숫자 형식 변환 테스트")
    void testFormatNumber() {
        // given
        Long number = 123L;
        int length = 10;

        // when
        String result1 = DataFormatUtil.formatNumber(number, length, PaymentConstants.NUMBER_BASIC);
        String result2 = DataFormatUtil.formatNumber(number, length, PaymentConstants.NUMBER_ZERO);
        String result3 = DataFormatUtil.formatNumber(number, length, PaymentConstants.NUMBER_LEFT);

        // then
        assertEquals(result1, "       123");
        assertEquals(result2, "0000000123");
        assertEquals(result3, "123       ");
    }

    @Test
    @DisplayName("문자열 형식 변환 테스트")
    void testFormatString() {
        // given
        String str = "test";
        int length = 10;
        // when
        String result = DataFormatUtil.formatString(str, 10);

        // then
        assertEquals("test      ", result);

    }

    @Test
    @DisplayName("바이트 길이 테스트")
    void testGetByteLength() {
        // given & when
        int length = DataFormatUtil.getByteLength("Hello");

        // then
        assertEquals(5, length);
    }

    @Test
    @DisplayName("문자열의 바이트 길이 - 한글 입력")
    void testGetByteLengthWithKorean() {
        int length = DataFormatUtil.getByteLength("안녕하세요");
        assertEquals(15, length); // UTF-8 인코딩 기준, 한글 1글자는 3바이트
    }

    @Test
    @DisplayName("카드 정보 암호화")
    void testEntryptCardInfo() {
        // given
        String cardNumber = "1234567891011112";
        String expiryDate = "0112";
        String cvc = "121";

        // when
        String result = DataFormatUtil.encryptCardInfo(cardNumber, expiryDate, cvc);

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("카드 정보 암호화 - Null 입력")
    void testEncryptCardInfoWithNull() {
        // given & when
        Exception exception1 = assertThrows(RuntimeException.class, () -> {
            DataFormatUtil.encryptCardInfo(null, "0112", "123");
        });

        Exception exception2 = assertThrows(RuntimeException.class, () -> {
            DataFormatUtil.encryptCardInfo("1234567891011112", null, "123");
        });

        Exception exception3 = assertThrows(RuntimeException.class, () -> {
            DataFormatUtil.encryptCardInfo("1234567891011112", "0112", null);
        });

        // then
        assertEquals("cardNumber cannot be null", exception1.getMessage());
        assertEquals("expiryDate cannot be null", exception2.getMessage());
        assertEquals("cvc cannot be null", exception3.getMessage());
    }

    @Test
    @DisplayName("부가가치세 계산")
    void testSetVat() {
        // given
        Long vat = 1000L;
        Long amount = 11000L;

        // when
        Long result = DataFormatUtil.setVat(vat, amount);

        // then
        assertEquals(1000L, result);
    }

    @Test
    @DisplayName("부가가치세가 null인 경우")
    void testSetVat_null() {
        // given
        Long vat = null;
        Long amount = 11000L;

        // when
        Long result = DataFormatUtil.setVat(vat, amount);

        // then
        assertEquals(1000L, result);
    }
}
