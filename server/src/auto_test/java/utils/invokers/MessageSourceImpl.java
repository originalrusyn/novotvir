package utils.invokers;

import lombok.Setter;
import novotvir.service.impl.CustomMessageSourceImpl;

import java.util.Locale;

// @author: Mykhaylo Titov on 31.12.14 00:28.
public class MessageSourceImpl extends CustomMessageSourceImpl {

    @Setter Locale locale;

    @Override
    public String getMessage(String code, Object... args){
        return getMessage(code, args, locale);
    }
}
