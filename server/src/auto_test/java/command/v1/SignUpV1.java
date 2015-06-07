package command.v1;

import command.AbstractCommand;
import command.SignUp;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import web.account.dto.AccountDto;
import web.signup.email.dto.UserRegDetailsDto;

import java.util.Locale;

import static java.util.Objects.nonNull;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.SEE_OTHER;

// @author: Mykhaylo Titov on 17.01.15 17:31.
public class SignUpV1 extends AbstractCommand implements SignUp {

    public static final String SIGNUP = "/signup";

    public SignUpV1(HttpEntity<MultiValueMap<String, String>> reqEntity, ResponseEntity<?> respEntity) {
        super(reqEntity, respEntity);
    }

    public static HttpEntity<MultiValueMap<String, String>> getHttpEntity(UserRegDetailsDto userRegDetailsDto, Locale locale) {
        return new HttpEntity<>(getRequestParamMap(userRegDetailsDto), getHttpHeaders(locale));
    }

    private static MultiValueMap<String, String> getRequestParamMap(UserRegDetailsDto userRegDetailsDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(EMAIL, userRegDetailsDto.getEmail());
        params.add(TOKEN, userRegDetailsDto.getToken());
        return params;
    }

    public String getReqParamEmail(){
        return reqEntity.getBody().getFirst(EMAIL);
    }

    public String getReqParamToken(){
        return reqEntity.getBody().getFirst(TOKEN);
    }

    public Locale getReqLocale(){
        return new Locale.Builder().setLanguageTag(reqEntity.getHeaders().get(ACCEPT_LANGUAGE).get(0)).build();
    }

    public AccountDto getRespAccountDto(){
        return (AccountDto) getRespEntity().getBody();
    }

    @Override
    public boolean isRespValid() {
        assertThat(respEntity.getStatusCode(), is(SEE_OTHER));
        assertThat(respEntity.getBody(), is(notNullValue()));

        return respEntity.getStatusCode().equals(SEE_OTHER) && nonNull(respEntity.getBody());
    }
}
