package util;

import org.androidannotations.annotations.EBean;

// @author: Mykhaylo Titov on 25.04.15 21:04.
@EBean
public class EmailFormatValidator {
    public boolean isValid(String email){
        return email.contains("@");
    }
}
