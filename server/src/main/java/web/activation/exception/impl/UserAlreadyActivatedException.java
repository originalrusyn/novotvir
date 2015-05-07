package web.activation.exception.impl;

import web.activation.exception.CouldNotActivateUserException;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
public class UserAlreadyActivatedException extends CouldNotActivateUserException {
    public static final String USER_ALREADY_ACTIVATED_EXCEPTION_MESSAGE_CODE = "activation.cannot.be.completed.unsupported.operation";

    public UserAlreadyActivatedException(String message, String localizedMessage) {
        super(message, localizedMessage);
    }

    @Override
    public String getErrCode() {
        return USER_ALREADY_ACTIVATED_EXCEPTION_MESSAGE_CODE;
    }
}
