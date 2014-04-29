package novotvir.security.encoder.impl;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.math.BigInteger;

/**
 * @author Titov Mykhaylo (titov)
 *         09.02.14 18:23
 */
public class PasswordEncoder extends Md5PasswordEncoder {

    @Override
    public String encodePassword(String rawPass, Object salt){
        String message = System.currentTimeMillis() + (String) salt + rawPass;
        byte[] messageDigest = getMessageDigest().digest(message.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String md5 = number.toString(16);

        while (md5.length() < 32) {
            md5 = "0" + md5;
        }
        return md5;
    }
}
