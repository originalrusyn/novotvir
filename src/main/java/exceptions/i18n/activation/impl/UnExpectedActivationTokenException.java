package exceptions.i18n.activation.impl;

import exceptions.i18n.activation.CouldNotActivateUserException;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
public class UnExpectedActivationTokenException extends CouldNotActivateUserException {
    public static final String messageCode = "activation.cannot.be.completed.unexpected.activation.token";

    private static final String USER_NAME = "userName";
    private static final String ACTUAL_TOKEN = "actualToken";
    private static final String defaultMessageTemplate = "Unexpected activation token [" + ACTUAL_TOKEN + "] for user with name [" + USER_NAME + "]";

    @Accessors(chain = true)
    public static class UnExpectedActivationTokenExceptionBuilder{
        private final Map<String, Object> messageArgsMap = new LinkedHashMap<>();

        public UnExpectedActivationTokenExceptionBuilder setActualToken(String actualToken){
            messageArgsMap.put(ACTUAL_TOKEN, actualToken);
            return this;
        }

        public UnExpectedActivationTokenExceptionBuilder setUserName(String userName){
            messageArgsMap.put(USER_NAME, userName);
            return this;
        }

        public UnExpectedActivationTokenException build(){
            String userName = (String) messageArgsMap.get(USER_NAME);
            String actualToken = (String) messageArgsMap.get(ACTUAL_TOKEN);

            String defaultMessage = defaultMessageTemplate.replaceAll(USER_NAME, userName).replaceAll(ACTUAL_TOKEN, actualToken);
            return new UnExpectedActivationTokenException(defaultMessage, messageArgsMap.values().toArray());
        }
    }

    private UnExpectedActivationTokenException(String defaultMessage, Object[] messageArgs) {
        super(defaultMessage, messageArgs);
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }
}
