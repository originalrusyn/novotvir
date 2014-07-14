package exceptions.i18n.activation.impl;

import exceptions.i18n.activation.CouldNotActivateUserException;
import lombok.experimental.Accessors;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
@Accessors(chain = true)
public class NoSuchUserException extends CouldNotActivateUserException {
    public static final String NO_SUCH_USER_EXCEPTION_MESSAGE_CODE = "activation.cannot.be.completed.unsupported.operation";

    public NoSuchUserException(String message,String localizedMessage) {
        super(message, localizedMessage);
    }

    @Override
    public String getErrCode() {
        return NO_SUCH_USER_EXCEPTION_MESSAGE_CODE;
    }

}
