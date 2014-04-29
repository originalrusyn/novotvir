package novotvir.service.impl;

import novotvir.persistence.domain.User;
import novotvir.service.CustomMessageSource;
import novotvir.service.MailService;
import novotvir.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Service;

import static novotvir.utils.RequestUtils.getServerURL;
import static org.springframework.social.support.URIBuilder.fromUri;

/**
 * @author Titov Mykhaylo (titov) on 28.04.2014.
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired MailSender mailSender;
    @Autowired SimpleMailMessage templateMessage;
    @Autowired CustomMessageSource customMessageSource;

    public SimpleMailMessage sendMailConfirmationLink(User user){
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);

        String emailValidationUrl = fromUri(getServerURL()).queryParam("userName", user.name).build().toString();

        String subj = customMessageSource.getMailValidationMailSubj();
        String text = customMessageSource.getMailValidationMailText(emailValidationUrl);

        msg.setTo(user.email);
        msg.setSubject(subj);
        msg.setText(text);
        mailSender.send(msg);
        return msg;
    }
}
