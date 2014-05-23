package exceptions.i18n.activation;

import exceptions.i18n.LocalizedException;

/**
 * @author Titov Mykhaylo (titov) on 19.05.2014.
 */
public abstract class CouldNotActivateUserException extends LocalizedException{

    protected CouldNotActivateUserException(String defaultMessage, Object[] messageArgs) {
        super(defaultMessage, messageArgs);
    }
}
