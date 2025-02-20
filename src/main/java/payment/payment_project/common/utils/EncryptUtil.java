package payment.payment_project.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Encrypt Util
 * 데이터 암호화 및 복호화를 제공하는 클래스
 *
 * @author winnie
 * @version 1.0
 * @since 2025-02-20
 */

@Slf4j
@Getter
public class EncryptUtil {

    private static String secretKey = "PAYMENT_SECRET_KEY";

    public static SecretKeySpec getSecretKey(String data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);
        key = sha.digest(key);
        key = Arrays.copyOf(key, 32);
        return new SecretKeySpec(key, "AES");
    }

    /**
     * 암호화
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        SecretKeySpec secretKeySpec = getSecretKey(data);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 복호화
     */
    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);

        return new String(cipher.doFinal(decodedBytes));
    }

}
