package utils.invokers;

import features.domain.*;
import novotvir.dto.AccountDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

    @Value("${environment_url}") String environmentUrl;
    @Resource RestTemplate restTemplate;

    public Person invoke(Person person){
        for (Device device : person.getDevices()) {

            Iterator<Email> emailIterator = person.getEmails().iterator();
            Iterator<Application> applicationIterator = device.getApplications().iterator();

            for (int i = 0; i < min(person.getEmails().size(), device.getApplications().size()); i++) {
                String email = emailIterator.next().getValue();
                Application application = applicationIterator.next();

                ResponseEntity<AccountDto> responseEntity = invoke(email, randomToken(), application.getLocale());

                assertThat(responseEntity.getStatusCode(), is(SEE_OTHER));
                assertThat(responseEntity.getBody(), is(notNullValue()));

                application.setAccount(new Account(person, responseEntity));
            }
        }
        return person;
    }

    private ResponseEntity<AccountDto> invoke(String email, String token, Locale locale) {
        HttpHeaders headers = new HttpHeaders ();
        headers.setAccept(asList(APPLICATION_JSON));
        headers.add("Accept-Language", locale.toString());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", email);
        params.add("token", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        return restTemplate.exchange(environmentUrl + "/signup", POST, httpEntity, AccountDto.class);
    }

}
