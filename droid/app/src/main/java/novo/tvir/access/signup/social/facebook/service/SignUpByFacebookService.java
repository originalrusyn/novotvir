package novo.tvir.access.signup.social.facebook.service;

import com.j256.ormlite.dao.Dao;
import dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.asm.AccountDtoAsm;
import novo.tvir.access.signup.service.SignUpRestService;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import persist.DBHelper;
import persist.domain.Account;

// @author: Mykhaylo Titov on 03.06.15 23:15.
@EBean
@Slf4j
public class SignUpByFacebookService {

    @Bean AccountDtoAsm accountDtoAsm;

    @RestService SignUpRestService signUpRestService;

    @StringRes(R.string.novotvir_base_url) String novotvirBaseUrl;

    @OrmLiteDao(helper = DBHelper.class) Dao<Account, Integer> accountDao;

    public boolean signup(String accessToken){
        try {
            signUpRestService.setRootUrl(novotvirBaseUrl);
            LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("accessToken", accessToken);
            ResponseEntity<AccountDto> responseEntity = signUpRestService.signupByFacebook(formData);
            Account account = accountDtoAsm.fromResponse(responseEntity);
            accountDao.createOrUpdate(account);
            return true;
        } catch (Exception e) {
            log.error("Can't sign in", e);
            return false;
        }
    }
}
