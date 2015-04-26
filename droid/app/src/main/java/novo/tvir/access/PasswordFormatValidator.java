package novo.tvir.access;

import org.androidannotations.annotations.EBean;

// @author: Mykhaylo Titov on 25.04.15 21:22.
@EBean
public class PasswordFormatValidator {
    public boolean isValid(String password){
        return password.length() > 4;
    }
}
