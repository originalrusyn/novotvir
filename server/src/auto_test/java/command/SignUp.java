package command;

import web.account.dto.AccountDto;

import java.util.Locale;

// @author: Mykhaylo Titov on 17.01.15 18:22.
public interface SignUp extends Command {

    String getReqParamEmail();
    String getReqParamToken();
    Locale getReqLocale();
    AccountDto getRespAccountDto();

}
