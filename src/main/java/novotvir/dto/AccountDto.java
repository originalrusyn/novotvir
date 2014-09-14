package novotvir.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import novotvir.persistence.domain.User;

// @author: Titov Mykhaylo (titov) on 13.09.14 20:36.
@Data
@Accessors(chain = true)
public class AccountDto {
    public static final String ACCOUNT_DTO="accountDto";

    public String email;
    public String name;
    public boolean activated;
    public boolean blocked;

    public static AccountDto accountDto(User user){
        return new AccountDto().setEmail(user.email).setName(user.name).setActivated(user.activated).setBlocked(user.blocked);
    }
}
