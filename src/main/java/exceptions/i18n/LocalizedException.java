package exceptions.i18n;

import lombok.Getter;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
public abstract class LocalizedException extends RuntimeException{

    @Getter
    protected Object[] messageArgs;

    protected LocalizedException(String defaultMessage, Object[] messageArgs) {
        super(defaultMessage);
        this.messageArgs = messageArgs;
    }

    public abstract String getMessageCode();
}
