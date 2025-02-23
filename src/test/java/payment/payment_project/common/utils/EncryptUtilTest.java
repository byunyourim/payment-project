package payment.payment_project.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EncryptUtilTest {

    @Test
    @DisplayName("암호화, 복호화 테스트")
    void testEncrypt() throws Exception {
        // given
        String data = "12345678910";

        // when
        String encryptedText = EncryptUtil.encrypt(data);
        String decryptedText = EncryptUtil.decrypt(encryptedText);

        // then
        assertNotNull(encryptedText);
        assertEquals(data, decryptedText);
    }

    @Test
    @DisplayName("다른 값을 리턴하는지 테스트")
    void testEncryptDifferentValues() throws Exception {
        // given
        String data1 = "12345678910";
        String data2 = "10987654321";

        // when
        String encryptedText1 = EncryptUtil.encrypt(data1);
        String encryptedText2 = EncryptUtil.encrypt(data2);

        // then
        assertNotEquals(encryptedText1, encryptedText2);
    }
}