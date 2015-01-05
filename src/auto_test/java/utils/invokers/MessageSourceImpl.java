package utils.invokers;

import novotvir.service.impl.CustomMessageSourceImpl;

import java.util.Locale;

// @author: Mykhaylo Titov on 31.12.14 00:28.
public class MessageSourceImpl extends CustomMessageSourceImpl {

    Locale locale;

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String getMessage(String code, Object... args){
        return getMessage(code, args, locale);
    }
}
