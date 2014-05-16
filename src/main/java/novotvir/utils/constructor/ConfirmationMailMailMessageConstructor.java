package novotvir.utils.constructor;

import novotvir.persistence.domain.User;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Titov Mykhaylo (titov) on 16.05.2014.
 */
public interface ConfirmationMailMailMessageConstructor {
    SimpleMailMessage construct(User user);
}
