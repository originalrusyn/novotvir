package novotvir.service.impl;

import novotvir.dto.UserRegDetailsDto;
import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static novotvir.utils.RequestUtils.getRemoteAddr;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author Titov Mykhaylo (titov)
 *         11.01.14 21:05
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(propagation = REQUIRED)
    public User updateLastWebLoginInfo(User user) {
        return save(user.setLastSignInIpAddress(getRemoteAddr()).setLastWebSignInTimestamp(new Date()));
    }

    @Override
    @Transactional(propagation = REQUIRED)
    public User registerUser(UserRegDetailsDto userRegDetailsDto) {
        String email = userRegDetailsDto.email;
        String token = userRegDetailsDto.token;
        return save(new User().setName(email).setEmail(email).setToken(token).setLastSignInIpAddress(getRemoteAddr()));
    }

    @Override
    @Transactional(propagation = REQUIRED)
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByName(String userName) {
        return userRepository.findByName(userName);
    }

    @Override
    public User findOne(long userId) {
        return userRepository.findOne(userId);
    }
}