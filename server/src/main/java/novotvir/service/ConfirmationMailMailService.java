package novotvir.service;

import novotvir.persistence.domain.User;
import org.springframework.mail.MailMessage;

// @author Titov Mykhaylo (titov) on 28.04.2014.
public interface ConfirmationMailMailService {
    MailMessage sendMailConfirmationLink(User user);
}
