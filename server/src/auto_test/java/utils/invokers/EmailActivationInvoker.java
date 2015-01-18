package utils.invokers;

import app.service.AccountActivationService;
import command.AbstractCommand;
import command.Command;
import command.SignUp;
import command.v1.SignUpV1;
import features.domain.*;
import novotvir.service.CustomMessageSource;
import org.springframework.stereotype.Component;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.mail.Message.RecipientType.TO;

// @author: Mykhaylo Titov on 04.01.15 22:45.
@Component
public class EmailActivationInvoker {
    static final String activationUrlRegex = ".+?/users/.+?activationToken=.+?&_method=PUT";

    @Resource Wiser mockSMTPServer;
    @Resource ServerMessageSourceResolver serverMessageSourceResolver;
    @Resource AccountActivationService accountActivationService;

    public Person invoke(Person person){
        try {
            List<WiserMessage> wiserMessages = mockSMTPServer.getMessages();
            for (WiserMessage wiserMessage : wiserMessages) {
                MimeMessage mimeMessage = wiserMessage.getMimeMessage();
                Address[] recipients = mimeMessage.getRecipients(TO);
                for (Address recipient : recipients) {
                    for (Device device : person.getDevices()) {
                        for (Application application : device.getApplications()) {
                            for (Command command : application.getCommandHistory()) {
                                if(command instanceof SignUp && recipient.toString().equals(((SignUp) command).getReqParamEmail())) {
                                    String content = (String) mimeMessage.getContent();
                                    CustomMessageSource messageSource = serverMessageSourceResolver.getMessageSource(((SignUp) command).getReqLocale());

                                    Pattern p = Pattern.compile("\\Q" + messageSource.getMailValidationMailText("\\E(" + activationUrlRegex + ")\\Q") + "\\E");
                                    Matcher matcher = p.matcher(content);
                                    matcher.find();
                                    String url = matcher.group(1);
                                    accountActivationService.invoke(application, url);
                                }
                            }
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
