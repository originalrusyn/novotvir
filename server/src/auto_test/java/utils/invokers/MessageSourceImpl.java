package utils.invokers;

import novotvir.service.impl.CustomMessageSourceImpl;
import org.springframework.stereotype.Service;

// @author: Mykhaylo Titov on 31.12.14 00:28.
@Service
public class MessageSourceImpl extends CustomMessageSourceImpl {
    @Override
    public String getMessage(String code, Object... args){
        return getMessage(code, args, null);
    }
}
