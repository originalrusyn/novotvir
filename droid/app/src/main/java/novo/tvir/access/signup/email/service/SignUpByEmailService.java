package novo.tvir.access.signup.email.service;

import asm.AccountDtoAsm;
import com.j256.ormlite.dao.Dao;
import dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.encoder.MD5PasswordEncoder;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import persist.DBHelper;
import persist.domain.Account;

// @author: Mykhaylo Titov on 12.04.15 19:50.
@EBean
@Slf4j
public class SignUpByEmailService {

    @Bean AccountDtoAsm accountDtoAsm;

    @Bean MD5PasswordEncoder md5PasswordEncoder;

    @RestService SignUpByEmailRestService signUpByEmailRestService;

    @StringRes(R.string.novotvir_base_url) String novotvirBaseUrl;
    @StringRes(R.string.novotvir_salt) String salt;

    @OrmLiteDao(helper = DBHelper.class) Dao<Account, Integer> accountDao;

    public boolean signUp(String mEmail, String mPassword){
        try {
            String token = md5PasswordEncoder.encodePassword(mPassword, salt);
            signUpByEmailRestService.setRootUrl(novotvirBaseUrl);
            LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("email", mEmail);
            formData.add("token", token);
            ResponseEntity<AccountDto> responseEntity = signUpByEmailRestService.signUp(formData);
            Account account = accountDtoAsm.fromResponse(responseEntity);
            accountDao.createOrUpdate(account);
            return true;
        } catch (Exception e) {
            log.error("Can't sign up", e);
            return false;
        }
    }
}
