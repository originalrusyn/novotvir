package util;

import org.androidannotations.annotations.EBean;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

// @author: Mykhaylo Titov on 25.04.15 21:04.
@EBean(scope = Singleton)
public class EmailFormatValidator {
    public boolean isValid(String email){
        return email.contains("@");
    }
}
