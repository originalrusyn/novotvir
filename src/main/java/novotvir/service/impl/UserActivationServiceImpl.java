package novotvir.service.impl;

import lombok.extern.slf4j.Slf4j;
import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.service.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static exceptions.i18n.activation.impl.NoSuchUserException.NoSuchUserExceptionBuilder;
import static exceptions.i18n.activation.impl.UnExpectedActivationTokenException.UnExpectedActivationTokenExceptionBuilder;
import static exceptions.i18n.activation.impl.UserAlreadyActivatedException.UserAlreadyActivatedExceptionBuilder;
import static java.util.Objects.isNull;

/**
 * @author Titov Mykhaylo (titov) on 16.05.2014.
 */
@Slf4j
@Service("userActivationService")
public class UserActivationServiceImpl implements UserActivationService {

    @Autowired UserRepository userRepository;

    @Override
    public User activate(String userName, String activationToken) {
        User user = userRepository.findByName(userName);
        if(isNull(user)) {
            log.info("Couldn't find user with name [{}]", userName);
            throw new NoSuchUserExceptionBuilder().setUserName(userName).build();
        } else if(!user.activationToken.equals(activationToken)){
            log.info("Expected activation token [{}] for user with name [{}] and actual activated token [{}] aren't the same", user.activationToken, user.name, activationToken);
            throw new UnExpectedActivationTokenExceptionBuilder().setActualToken(activationToken).setUserName(userName).build();
        } else if (user.activated){
            log.info("User with name [{}] had been already activated", userName);
            throw new UserAlreadyActivatedExceptionBuilder().setUserName(userName).build();
        }
        return userRepository.save(user.setActivated(true));
    }
}
