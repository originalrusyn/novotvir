package novotvir.service.impl;

import exceptions.i18n.activation.impl.NoSuchUserException;
import exceptions.i18n.activation.impl.UnExpectedActivationTokenException;
import exceptions.i18n.activation.impl.UserAlreadyActivatedException;
import lombok.extern.slf4j.Slf4j;
import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.service.CustomMessageSource;
import novotvir.service.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static exceptions.i18n.activation.impl.NoSuchUserException.NO_SUCH_USER_EXCEPTION_MESSAGE_CODE;
import static exceptions.i18n.activation.impl.UnExpectedActivationTokenException.UN_EXPECTED_ACTIVATION_TOKEN_EXCEPTION_MESSAGE_CODE;
import static exceptions.i18n.activation.impl.UserAlreadyActivatedException.USER_ALREADY_ACTIVATED_EXCEPTION_MESSAGE_CODE;
import static java.util.Objects.isNull;

// @author Titov Mykhaylo (titov) on 16.05.2014.
@Slf4j
@Service("userActivationService")
public class UserActivationServiceImpl implements UserActivationService {

    @Resource UserRepository userRepository;
    @Autowired CustomMessageSource customMessageSource;

    @Override
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
