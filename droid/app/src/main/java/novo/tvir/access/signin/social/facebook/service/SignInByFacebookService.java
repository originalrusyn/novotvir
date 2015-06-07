package novo.tvir.access.signin.social.facebook.service;

import asm.AccountDtoAsm;
import com.j256.ormlite.dao.Dao;
import dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
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
public class SignInByFacebookService {

    @Bean AccountDtoAsm accountDtoAsm;

    @RestService SignInByFacebookRestService signInByFacebookRestService;

    @StringRes(R.string.novotvir_base_url) String novotvirBaseUrl;

    @OrmLiteDao(helper = DBHelper.class) Dao<Account, Integer> accountDao;

    public boolean signIn(String accessToken){
        try {
            signInByFacebookRestService.setRootUrl(novotvirBaseUrl);
            LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("accessToken", accessToken);
            ResponseEntity<AccountDto> responseEntity = signInByFacebookRestService.signIn(formData);
            Account account = accountDtoAsm.fromResponse(responseEntity);
            accountDao.createOrUpdate(account);
            return true;
        } catch (Exception e) {
            log.error("Can't sign in", e);
            return false;
        }
    }
}
