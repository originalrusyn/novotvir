package web.signup.email.service.impl;

import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import web.persistence.domain.User;
import web.signup.constructor.ConfirmationMailMailMessageConstructor;

import javax.annotation.Resource;

// @author Titov Mykhaylo (titov) on 28.04.2014.
@Service("confirmationMailMailService")
public class ConfirmationMailMailServiceImpl {

    @Resource MailSender mailSender;
    @Resource ConfirmationMailMailMessageConstructor mailMessageConstructor;

    public MailMessage sendMailConfirmationLink(User user){
        SimpleMailMessage msg = mailMessageConstructor.construct(user);
        mailSender.send(msg);
        return msg;
    }
}
