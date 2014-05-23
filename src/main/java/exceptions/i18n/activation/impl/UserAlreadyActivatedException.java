package exceptions.i18n.activation.impl;

import exceptions.i18n.activation.CouldNotActivateUserException;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
public class UserAlreadyActivatedException extends CouldNotActivateUserException {
    public static final String messageCode = "activation.cannot.be.completed.unsupported.operation";

    public static final String USER_NAME = "userName";
    private static final String defaultMessageTemplate = "Unsupported operation for user with name [" + USER_NAME + "]";

    @Accessors(chain = true)
    public static class UserAlreadyActivatedExceptionBuilder{
        @Setter private String userName;

        public UserAlreadyActivatedException build(){
            String defaultMessage = defaultMessageTemplate.replaceAll(USER_NAME, userName);
            return new UserAlreadyActivatedException(defaultMessage, new Object[]{userName});
        }
    }

    private UserAlreadyActivatedException(String message, Object[] messageArgs) {
        super(message, messageArgs);
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }
}
