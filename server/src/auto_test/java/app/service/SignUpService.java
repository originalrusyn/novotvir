package app.service;

import command.SignUp;
import command.v1.SignUpV1;
import feature.domain.Application;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import web.account.dto.AccountDto;
import web.signup.email.dto.UserRegDetailsDto;

import static feature.domain.Application.randomToken;
import static org.springframework.http.HttpMethod.POST;

// @author: Mykhaylo Titov on 09.01.15 23:05.
@Service
public class SignUpService extends AppService{

    public SignUp invoke(Application application, String email){

        UserRegDetailsDto userRegDetailsDto = new UserRegDetailsDto().setEmail(email).setToken(randomToken());

        HttpEntity<MultiValueMap<String, String>> httpEntity = SignUpV1.getHttpEntity(userRegDetailsDto, application.getLocale());

        ResponseEntity<AccountDto> respEntity = restTemplate.exchange(environmentUrl + SignUpV1.SIGNUP, POST, httpEntity, AccountDto.class);

        return application.signUp(new SignUpV1(httpEntity, respEntity));
    }
}
