package web.activation.exception;

import common.exception.i18n.LocalizedException;

/**
 * @author Titov Mykhaylo (titov) on 19.05.2014.
 */
public abstract class CouldNotActivateUserException extends LocalizedException{

    public CouldNotActivateUserException(String message, String localizedMessage){
        super(message);
        this.localizedMessage=localizedMessage;
    }
}
