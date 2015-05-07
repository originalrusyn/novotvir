package common.service.impl;

import lombok.Delegate;
import common.service.CustomMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import static common.util.RequestUtils.getRequestLocale;

// @author Titov Mykhaylo (titov) on 13.03.14.
@Service("customMessageSource")
public class CustomMessageSourceImpl implements CustomMessageSource {

    @Autowired
    @Delegate
    ReloadableResourceBundleMessageSource messageSource;

    @Override
    public String getMailValidationMailSubj(){
        return getMessage("email.validation.email.subj");
    }

    @Override
    public String getMailValidationMailText(String emailValidationUrl){
        return getMessage("email.validation.email.txt", emailValidationUrl);
    }


    @Override
    public String getMessage(String code, Object... args){
        return getMessage(code, args, getRequestLocale());
    }
}
