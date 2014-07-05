package exceptions.i18n.activation.impl;

import exceptions.i18n.activation.CouldNotActivateUserException;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
public class UnExpectedActivationTokenException extends CouldNotActivateUserException {
    public static final String UN_EXPECTED_ACTIVATION_TOKEN_EXCEPTION_MESSAGE_CODE = "activation.cannot.be.completed.unexpected.activation.token";

    public UnExpectedActivationTokenException(String message, String localizedMessage) {
        super(message, localizedMessage);
    }

    @Override
    public String getErrCode() {
        return UN_EXPECTED_ACTIVATION_TOKEN_EXCEPTION_MESSAGE_CODE;
    }

}
