package utils.invokers;

import novotvir.service.CustomMessageSource;

import java.util.List;
import java.util.Locale;

// @author Titov Mykhaylo (titov) on 05.01.2015.
public class ServerMessageSourceResolver {
    List<MessageSourceImpl> messageSources;

    public CustomMessageSource getMessageSource(Locale locale){
        return messageSources.stream().filter(x->x.locale.equals(locale)).findFirst().get();
    }
}
