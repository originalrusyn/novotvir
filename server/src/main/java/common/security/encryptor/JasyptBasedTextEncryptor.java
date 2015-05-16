package common.security.encryptor;


import lombok.Setter;
import org.springframework.security.crypto.encrypt.TextEncryptor;

// @author: Mykhaylo Titov on 16.05.15 13:40.
public class JasyptBasedTextEncryptor implements TextEncryptor {

    @Setter org.jasypt.util.text.TextEncryptor textEncryptor;

    @Override
    public String encrypt(String text) {
        return textEncryptor.encrypt(text);
    }

    @Override
    public String decrypt(String encryptedText) {
        return textEncryptor.decrypt(encryptedText);
    }
}
