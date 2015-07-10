package web.account.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import web.persistence.domain.User;

import static lombok.AccessLevel.PUBLIC;

// @author: Titov Mykhaylo (titov) on 13.09.14 20:36.
@SuppressFBWarnings({"USBR_UNNECESSARY_STORE_BEFORE_RETURN"})
@Data
@Accessors(chain = true)
@FieldDefaults(level = PUBLIC)
public class AccountDto {
    public static final String ACCOUNT_DTO="accountDto";

    String imageUrl;
    String displayName;
    String email;
    String name;
    boolean activated;
    boolean blocked;

    public static AccountDto accountDto(User user){
        return new AccountDto().setEmail(user.primaryEmailAddress.email).setName(user.name).setActivated(user.activated).setBlocked(user.blocked);
    }
}
