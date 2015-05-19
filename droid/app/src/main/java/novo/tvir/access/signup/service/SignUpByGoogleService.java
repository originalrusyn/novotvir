package novo.tvir.access.signup.service;

import com.j256.ormlite.dao.Dao;
import dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.asm.AccountDtoAsm;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import persist.DBHelper;
import persist.domain.Account;

// @author: m on 01.05.15 19:07.
@EBean
@Slf4j
public class SignUpByGoogleService {
    @Bean AccountDtoAsm accountDtoAsm;

    @RestService SignUpRestService signUpRestService;

    @StringRes(R.string.novotvir_base_url) String novotvirBaseUrl;

    @OrmLiteDao(helper = DBHelper.class) Dao<Account, Integer> accountDao;

    public void signup(String code) throws Exception {
        signUpRestService.setRootUrl(novotvirBaseUrl);
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        ResponseEntity<AccountDto> responseEntity = signUpRestService.signupByGoogle(formData);
        Account account = accountDtoAsm.fromResponse(responseEntity);
        accountDao.createOrUpdate(account);
    }

}
