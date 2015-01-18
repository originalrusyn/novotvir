package novotvir.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import novotvir.persistence.domain.User;

import static lombok.AccessLevel.PUBLIC;

// @author: Titov Mykhaylo (titov) on 13.09.14 20:36.
@Data
@Accessors(chain = true)
@FieldDefaults(level = PUBLIC)
public class AccountDto {
    public static final String ACCOUNT_DTO="accountDto";

    String email;
    String name;
    boolean activated;
    boolean blocked;

    public static AccountDto accountDto(User user){
        return new AccountDto().setEmail(user.email).setName(user.name).setActivated(user.activated).setBlocked(user.blocked);
    }
}
