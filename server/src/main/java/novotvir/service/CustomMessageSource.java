package novotvir.service;

import org.springframework.context.HierarchicalMessageSource;

import java.util.Locale;

// @author Titov Mykhaylo (titov) on 19.03.2014.
public interface CustomMessageSource extends HierarchicalMessageSource {

    Locale uk_UA_LOCALE = new Locale("uk", "UA");

    String getMailValidationMailSubj();
    String getMailValidationMailText(String emailValidationUrl);
    String getMessage(String code, Object... args);
}
