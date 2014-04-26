package novotvir.service.impl;

import lombok.Delegate;
import novotvir.service.CustomMessageSource;
import novotvir.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author Titov Mykhaylo (titov) on 13.03.14.
 */
@Service("customMessageSource")
public class CustomMessageSourceImpl implements CustomMessageSource {

    @Autowired
    @Delegate
    ReloadableResourceBundleMessageSource messageSource;

    private Locale getRequestLocale() {
        HttpServletRequest request = RequestUtils.getHttpServletRequest();
        return request.getLocale();
    }
}
