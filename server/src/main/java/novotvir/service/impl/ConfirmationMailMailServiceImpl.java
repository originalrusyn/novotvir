package novotvir.service.impl;

import novotvir.persistence.domain.User;
import novotvir.service.ConfirmationMailMailService;
import novotvir.utils.constructor.ConfirmationMailMailMessageConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

// @author Titov Mykhaylo (titov) on 28.04.2014.
@Service("confirmationMailMailService")
public class ConfirmationMailMailServiceImpl implements ConfirmationMailMailService {

    @Autowired MailSender mailSender;
    @Autowired ConfirmationMailMailMessageConstructor mailMessageConstructor;

    public MailMessage sendMailConfirmationLink(User user){
        SimpleMailMessage msg = mailMessageConstructor.construct(user);
        mailSender.send(msg);
        return msg;
    }
}
