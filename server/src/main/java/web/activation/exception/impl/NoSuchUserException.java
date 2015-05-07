package web.activation.exception.impl;

import lombok.experimental.Accessors;
import web.activation.exception.CouldNotActivateUserException;

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
