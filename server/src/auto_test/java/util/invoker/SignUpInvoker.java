package util.invoker;

import app.service.SignUpService;
import feature.domain.Application;
import feature.domain.Device;
import feature.domain.Email;
import feature.domain.Person;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;

import static java.lang.Math.min;

// @author: Mykhaylo Titov on 11.09.14 22:59.
@Component
public class SignUpInvoker {

    @Resource SignUpService signUpService;

    public Person invoke(Person person){
        for (Device device : person.getDevices()) {

            Iterator<Email> emailIterator = person.getEmails().iterator();
            Iterator<Application> applicationIterator = device.getApplications().iterator();

            for (int i = 0; i < min(person.getEmails().size(), device.getApplications().size()); i++) {
                String email = emailIterator.next().getValue();
                Application application = applicationIterator.next();

                signUpService.invoke(application, email);
            }
        }
        return person;
    }

}
