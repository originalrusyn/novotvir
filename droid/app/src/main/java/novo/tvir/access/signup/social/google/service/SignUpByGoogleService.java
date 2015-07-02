package novo.tvir.access.signup.social.google.service;

import com.j256.ormlite.dao.Dao;
import dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import asm.AccountDtoAsm;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import persist.DBHelper;
import persist.domain.Account;

// @author: Mykhaylo Titov on 01.05.15 19:07.
@EBean
@Slf4j
public class SignUpByGoogleService {
    @Bean AccountDtoAsm accountDtoAsm;

    @RestService SignUpByGoogleRestService signUpByGoogleRestService;

    @StringRes(R.string.novotvir_base_url) String novotvirBaseUrl;

    @OrmLiteDao(helper = DBHelper.class) Dao<Account, Integer> accountDao;

    public Account signUp(String code){
        try {
            signUpByGoogleRestService.setRootUrl(novotvirBaseUrl);
            LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("code", code);
            ResponseEntity<AccountDto> responseEntity = signUpByGoogleRestService.signUp(formData);
            Account account = accountDtoAsm.fromResponse(responseEntity);
            accountDao.createOrUpdate(account);
            return account;
        } catch (Exception e) {
            log.error("Can't sign up", e);
            return null;
        }
    }

}
