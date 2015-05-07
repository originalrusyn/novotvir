package app.service;

import command.ActivateAccount;
import command.v1.ActivateAccountV1;
import feature.domain.Application;
import lombok.extern.slf4j.Slf4j;
import web.account.dto.AccountDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import static org.springframework.http.HttpMethod.GET;

// @author: Mykhaylo Titov on 10.01.15 19:47.
@Service
@Slf4j
public class AccountActivationService extends AppService {

    public ActivateAccount invoke(Application application, String url){

        HttpEntity<MultiValueMap<String, String>> httpEntity = ActivateAccountV1.getHttpEntity(application.getLocale());

        ResponseEntity<AccountDto> responseEntity = restTemplate.exchange(url, GET, httpEntity, AccountDto.class);

        return new ActivateAccountV1(httpEntity, responseEntity);
    }
}
