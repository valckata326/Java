package bg.sofia.uni.fmi.mjt.git;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {
    static public String hexDigest(String input) throws NoSuchAlgorithmException {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return convertBytesToHex(bytes);
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }

    }

    static private String convertBytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte current : bytes) {
            hex.append(String.format("%02x", current));
        }

        return hex.toString();
    }
}