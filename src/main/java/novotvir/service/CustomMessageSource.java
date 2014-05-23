package novotvir.service;

import org.springframework.context.HierarchicalMessageSource;

/**
 * @author Titov Mykhaylo (titov) on 19.03.2014.
 */
public interface CustomMessageSource extends HierarchicalMessageSource {

    String getMailValidationMailSubj();
    String getMailValidationMailText(String emailValidationUrl);
}
