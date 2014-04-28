package novotvir.service.impl;

import novotvir.persistence.domain.User;
import novotvir.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author Titov Mykhaylo (titov) on 28.04.2014.
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired MailSender mailSender;
    @Autowired SimpleMailMessage templateMessage;

    public SimpleMailMessage sendMailConfirmationLink(User user){
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(user.email);
        msg.setSubject("sbj");
        msg.setText("hi");
        mailSender.send(msg);
        return msg;
    }
}
