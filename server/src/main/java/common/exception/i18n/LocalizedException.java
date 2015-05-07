package common.exception.i18n;

import lombok.Getter;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
public abstract class LocalizedException extends RuntimeException{
    @Getter public String localizedMessage;

    public LocalizedException(String message){
        super(message);
    }

    public abstract String getErrCode();
}
