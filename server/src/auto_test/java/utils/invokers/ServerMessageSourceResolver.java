package utils.invokers;

import novotvir.service.CustomMessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

// @author Titov Mykhaylo (titov) on 05.01.2015.
@Service
public class ServerMessageSourceResolver {
    @Resource List<MessageSourceImpl> messageSources;

    public CustomMessageSource getMessageSource(Locale locale){
        return messageSources.stream().filter(x->x.locale.equals(locale)).findFirst().get();
    }
}
