package app.tests.features.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import novotvir.dto.AccountDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

// @author: Mykhaylo Titov on 13.09.14 12:54.
@Data
@Accessors(chain = true)
public class Account {
    final AccountDto lastAccountDto;
    final String lastRememberMeToken;

    public Account(ResponseEntity<AccountDto> responseEntity){
        this.lastAccountDto = responseEntity.getBody();
        HttpHeaders headers = responseEntity.getHeaders();
        this.lastRememberMeToken = headers.get("Set-Cookie").get(0);
    }
}
