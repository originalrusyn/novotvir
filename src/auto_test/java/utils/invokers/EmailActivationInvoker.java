package utils.invokers;

import features.domain.Email;
import features.domain.Person;
import novotvir.dto.AccountDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static javax.mail.Message.RecipientType.TO;
import static org.springframework.http.MediaType.APPLICATION_JSON;

// @author: Mykhaylo Titov on 04.01.15 22:45.
@Component
public class EmailActivationInvoker {
    static final String activationUrlRegex = ".+?/users/.+?activationToken=.+?&_method=PUT";

    @Resource Wiser mockSMTPServer;
    @Resource MessageSourceImpl messageSourceImpl;
    @Resource RestTemplate restTemplate;

    public Person invoke(Person person){
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
                            Pattern p = Pattern.compile("\\Q" + messageSourceImpl.getMailValidationMailText("\\E("+activationUrlRegex+")\\Q") + "\\E");
                            Matcher matcher = p.matcher(content);
                            matcher.find();
                            String url = matcher.group(1);

                            HttpHeaders headers = new HttpHeaders ();
                            headers.setAccept(asList(APPLICATION_JSON));
                            //headers.add("Accept-Language", locale.toString());

                            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(null, headers);

                            ResponseEntity<AccountDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, AccountDto.class);
                            break;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return person;
    }
}
