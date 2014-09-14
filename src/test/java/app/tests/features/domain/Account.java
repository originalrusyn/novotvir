package app.tests.features.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import novotvir.dto.AccountDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

// @author: Mykhaylo Titov on 13.09.14 12:54.
@Data
@Accessors(chain = true)
public class Account {
    final AccountDto lastAccountDto;
    String lastRememberMeToken;

    public Account(ResponseEntity<AccountDto> responseEntity){
        this.lastAccountDto = responseEntity.getBody();
        HttpHeaders headers = responseEntity.getHeaders();
        List<String> cookies = headers.get("Set-Cookie");
        if(isNotEmpty(cookies)) {
            this.lastRememberMeToken = cookies.get(0);
        }
    }
}
