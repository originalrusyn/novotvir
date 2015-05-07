package web.activation.service;

import lombok.extern.slf4j.Slf4j;
import common.service.CustomMessageSource;
import org.springframework.stereotype.Service;
import web.activation.exception.impl.NoSuchUserException;
import web.activation.exception.impl.UnExpectedActivationTokenException;
import web.activation.exception.impl.UserAlreadyActivatedException;
import web.persistence.domain.User;
import web.persistence.repository.UserRepository;

import javax.annotation.Resource;

import static java.util.Objects.isNull;
import static web.activation.exception.impl.NoSuchUserException.NO_SUCH_USER_EXCEPTION_MESSAGE_CODE;
import static web.activation.exception.impl.UnExpectedActivationTokenException.UN_EXPECTED_ACTIVATION_TOKEN_EXCEPTION_MESSAGE_CODE;
import static web.activation.exception.impl.UserAlreadyActivatedException.USER_ALREADY_ACTIVATED_EXCEPTION_MESSAGE_CODE;

// @author Titov Mykhaylo (titov) on 16.05.2014.
@Slf4j
@Service("userActivationService")
public class UserActivationServiceImpl  {

    @Resource UserRepository userRepository;
    @Resource CustomMessageSource customMessageSource;

    public User activate(String userName, String activationToken) {
        User user = userRepository.findByName(userName);
        if(isNull(user)) {
            throwNoSuchUserException(userName);
        } else if(!user.activationToken.equals(activationToken)){
            throwUnExpectedActivationTokenException(userName, activationToken, user);
        } else if (user.activated){
            throwUserAlreadyActivatedException(userName);
        }
        return userRepository.save(user.setActivated(true));
    }

    private User throwNoSuchUserException(String userName) {
        String message = "Couldn't find user with name ["+userName+"]";
        String localizedMessage = customMessageSource.getMessage(NO_SUCH_USER_EXCEPTION_MESSAGE_CODE, userName);
        throw new NoSuchUserException(message, localizedMessage);
    }

    private User throwUnExpectedActivationTokenException(String userName, String activationToken, User user) {
        String message = "Expected activation token ["+user.activationToken+"] for user with name ["+user.name+"] and actual activated token ["+activationToken+"] aren't the same";
        String localizedMessage = customMessageSource.getMessage(UN_EXPECTED_ACTIVATION_TOKEN_EXCEPTION_MESSAGE_CODE, userName, activationToken);
        throw new UnExpectedActivationTokenException(message, localizedMessage);
    }

    private User throwUserAlreadyActivatedException(String userName) {
        String message = "User with name ["+userName+"] had been already activated";
        String localizedMessage = customMessageSource.getMessage(USER_ALREADY_ACTIVATED_EXCEPTION_MESSAGE_CODE, userName);
        throw new UserAlreadyActivatedException(message, localizedMessage);
    }
}
