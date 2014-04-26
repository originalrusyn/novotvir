package novotvir.security.encoder.impl;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * @author Titov Mykhaylo (titov)
 *         09.02.14 18:23
 */
public class PasswordEncoder extends Md5PasswordEncoder {

    @Override
    public String encodePassword(String rawPass, Object salt){
        return rawPass;
    }
}
