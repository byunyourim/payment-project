package payment.payment_project.common.utils;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class EncryptUtilTest {

    @Test
    @DisplayName("암호화, 복호화 테스트")
    void testEncrypt() throws Exception {
        String data = "12345678910";

        String encryptedText = EncryptUtil.encrypt(data);
        String decryptedText = EncryptUtil.decrypt(encryptedText);

        assertNotNull(encryptedText);
        assertEquals(data, decryptedText);
    }

    @Test
    @DisplayName("다른 값을 리턴하는지 테스트")
    void testEncryptDifferentValues() throws Exception {

        String data1 = "12345678910";
        String data2 = "10987654321";

        String encryptedText1 = EncryptUtil.encrypt(data1);
        String encryptedText2 = EncryptUtil.encrypt(data2);

        assertNotEquals(encryptedText1, encryptedText2);
    }
}