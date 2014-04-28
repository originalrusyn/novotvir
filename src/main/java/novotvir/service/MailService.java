package novotvir.service;

import novotvir.persistence.domain.User;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Titov Mykhaylo (titov) on 28.04.2014.
 */
public interface MailService {
    SimpleMailMessage sendMailConfirmationLink(User user);
}
