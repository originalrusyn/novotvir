package checker;

import util.invoker.MessageSourceImpl;
import feature.domain.Email;
import feature.domain.Person;
import org.springframework.stereotype.Component;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Set;

import static javax.mail.Message.RecipientType.TO;
import static org.junit.Assert.assertTrue;

// @author: Mykhaylo Titov on 19.10.14 22:10.
@Component
public class ActivationMailChecker {
    static final String activationUrlRegex = ".+?/users/.+?activationToken=.+?&_method=PUT";

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
                            String content = (String) mimeMessage.getContent();
                            String mailBodyPattern = "\\Q" + messageSourceImpl.getMailValidationMailText("\\E"+activationUrlRegex+"\\Q") + "\\E";
                            assertTrue(content.matches(mailBodyPattern));
                            break;
                        }
                    }
                }
            }
        }catch (Exception e){

        }
    }
}
