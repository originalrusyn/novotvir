package asm;

import dto.AccountDto;
import org.androidannotations.annotations.EBean;
import org.springframework.http.ResponseEntity;
import persist.domain.Account;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

// @author: Mykhaylo Titov on 12.04.15 19:40.
@EBean(scope = Singleton)
public class AccountDtoAsm {

    public Account fromResponse(ResponseEntity<AccountDto> responseEntity){
        AccountDto accountDto = responseEntity.getBody();
        String rememberMeToken = getRememberMeToken(responseEntity.getHeaders().getFirst("Set-Cookie"));

        return new Account().setName(accountDto.getName()).
                setEmail(accountDto.getEmail()).
                setActivated(accountDto.isActivated()).
                setBlocked(accountDto.isBlocked()).
                setRememberMeToken(rememberMeToken);
    }

    private String getRememberMeToken(String cookieHeader) {
        String[] cookies = cookieHeader.split(";");

        for (String cookie : cookies) {
            String[] cookieNameAndValue = cookie.split("=");
            if(cookieNameAndValue[0].equals("_REMEMBER_ME")){
                return cookieNameAndValue[1];
            }
        }
        return null;
    }
}
