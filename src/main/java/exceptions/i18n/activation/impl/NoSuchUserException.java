package exceptions.i18n.activation.impl;

import exceptions.i18n.activation.CouldNotActivateUserException;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
@Accessors(chain = true)
public class NoSuchUserException extends CouldNotActivateUserException {

    public static final String messageCode = "activation.cannot.be.completed.unsupported.operation";

    public static final String USER_NAME = "userName";
    private static final String defaultMessageTemplate = "Unsupported operation for user with name [" + USER_NAME + "]";

    @Accessors(chain = true)
    public static class NoSuchUserExceptionBuilder{
        @Setter private String userName;

        public NoSuchUserException build(){
            String defaultMessage = defaultMessageTemplate.replaceAll(USER_NAME, userName);
            return new NoSuchUserException(defaultMessage, new Object[]{userName});
        }
    }

    private NoSuchUserException(String defaultMessage, Object[] messageArgs) {
        super(defaultMessage, messageArgs);
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }
}
