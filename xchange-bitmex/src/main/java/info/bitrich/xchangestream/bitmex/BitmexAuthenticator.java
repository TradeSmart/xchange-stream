package info.bitrich.xchangestream.bitmex;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class BitmexAuthenticator {

    public static String generateSignature(String path, String method, String body, String secret, String expiry) {
        try {
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(secret_key);

            String message = method + path + expiry + body;

            return String.valueOf(Hex.encodeHex(sha256_HMAC.doFinal(message.getBytes())));

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Can't calculate signature for BitMEX authenticated call", e);
        }
    }

}
