package web.signup.constructor;

import web.persistence.domain.User;
import org.springframework.mail.SimpleMailMessage;

// @author Titov Mykhaylo (titov) on 16.05.2014.
public interface ConfirmationMailMailMessageConstructor {
    SimpleMailMessage construct(User user);
}
