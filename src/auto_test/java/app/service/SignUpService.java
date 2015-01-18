package app.service;

import command.SignUp;
import command.v1.SignUpV1;
import features.domain.Application;
import novotvir.dto.AccountDto;
import novotvir.dto.UserRegDetailsDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import static features.domain.Application.randomToken;
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
