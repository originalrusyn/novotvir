package novotvir.service.impl;

import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.service.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Titov Mykhaylo (titov) on 16.05.2014.
 */
@Service("userActivationService")
public class UserActivationServiceImpl implements UserActivationService {

    @Autowired UserRepository userRepository;

    @Override
    public User activate() {
        User user = userRepository.findByName("");
        return userRepository.save(user.setActivated(true));
    }
}
