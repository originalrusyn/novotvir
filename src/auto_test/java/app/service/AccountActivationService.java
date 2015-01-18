package app.service;

import command.ActivateAccount;
import command.v1.ActivateAccountV1;
import features.domain.Application;
import lombok.extern.slf4j.Slf4j;
import novotvir.dto.AccountDto;
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

        log.info("ggggggggg");

        HttpEntity<MultiValueMap<String, String>> httpEntity = ActivateAccountV1.getHttpEntity(application.getLocale());

        ResponseEntity<AccountDto> responseEntity = restTemplate.exchange(url, GET, httpEntity, AccountDto.class);

        return new ActivateAccountV1(httpEntity, responseEntity);
    }
}
