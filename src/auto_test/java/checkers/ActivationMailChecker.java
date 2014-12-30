package checkers;

import features.domain.Email;
import features.domain.Person;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import utils.invokers.MessageSourceImpl;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Set;

import static javax.mail.Message.RecipientType.TO;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

// @author: Mykhaylo Titov on 19.10.14 22:10.
@Component
public class ActivationMailChecker {
    @Resource Wiser mockSMTPServer;
    @Resource MessageSourceImpl messageSourceImpl;

    public void check(Person person) {
        try {
            List<WiserMessage> wiserMessages = mockSMTPServer.getMessages();
            for (WiserMessage wiserMessage : wiserMessages) {
                MimeMessage mimeMessage = wiserMessage.getMimeMessage();
                Address[] recipients = mimeMessage.getRecipients(TO);
                for (Address recipient : recipients) {
                    Set<Email> emails = person.getEmails();
                    for (Email email : emails) {
                        if(recipient.toString().equals(email.getValue())) {
                            assertThat(mimeMessage.getContent(), is(messageSourceImpl.getMailValidationMailText("")));
                            break;
                        }
                    }
                }
            }
        }catch (Exception e){
        }
    }
}
