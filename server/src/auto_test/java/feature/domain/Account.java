package feature.domain;

import command.SignUp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import web.account.dto.AccountDto;

import static org.junit.Assert.assertNotNull;

// @author: Mykhaylo Titov on 13.09.14 12:54.
@Getter
@Setter
@ToString(exclude = {"person"})
@Accessors(chain = true)
public class Account {
    final Person person;
    final String token;
    AccountDto lastAccountDto;

    public Account(Person person, SignUp signUp){
        assertNotNull(person);
        assertNotNull(signUp);

        signUp.isRespValid();

        this.lastAccountDto = signUp.getRespAccountDto();

        this.person = person;
        person.addAccount(this);
        this.token = signUp.getReqParamToken();
    }

    public String getEmail(){
        return lastAccountDto.getEmail();
    }
}
