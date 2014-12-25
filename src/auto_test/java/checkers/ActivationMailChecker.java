package checkers;

import features.domain.Email;
import features.domain.Person;
import org.springframework.stereotype.Component;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Set;

import static javax.mail.Message.RecipientType.TO;

// @author: Mykhaylo Titov on 19.10.14 22:10.
@Component
public class ActivationMailChecker {
    @Resource Wiser mockSMTPServer;

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
                            break;
                        }
                    }
                }
            }
        }catch (Exception e){

        }
    }
}
