package novo.tvir.access.signup.encoder;

import org.androidannotations.annotations.EBean;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// @author: Mykhaylo Titov on 12.04.15 10:00.
@EBean
public class MD5PasswordEncoder {

    public String encodePassword(String rawPassword, String salt) throws NoSuchAlgorithmException {
        String message = salt + rawPassword + salt;

        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(message.getBytes(Charset.forName("UTF-8")));
        byte messageDigest[] = digest.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2) {
                h = "0" + h;
            }
            hexString.append(h);
        }
        return hexString.toString();
    }

}
