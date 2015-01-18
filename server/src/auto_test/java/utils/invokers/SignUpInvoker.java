package utils.invokers;

import app.service.SignUpService;
import features.domain.*;
import novotvir.dto.AccountDto;
import novotvir.dto.UserRegDetailsDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Locale;

import static features.domain.Application.randomToken;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.SEE_OTHER;
import static org.springframework.http.MediaType.APPLICATION_JSON;

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
