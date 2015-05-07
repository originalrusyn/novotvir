package web.signup.service.impl;

import web.persistence.domain.User;
import web.persistence.repository.UserRepository;
import web.security.credential.impl.UserDetailsImpl;
import web.signup.dto.RegDto;
import web.signup.service.UserRegService;
import web.signup.email.generator.ActivationTokenGenerator;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static common.util.RequestUtils.getRemoteAddr;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

// @author Titov Mykhaylo (titov) on 07.05.2014.
@Service("userRegService")
public class UserRegServiceImpl implements UserRegService {

    @Resource UserRepository userRepository;
    @Resource SaltSource saltSource;
    @Resource PasswordEncoder passwordEncoder;
    @Resource ActivationTokenGenerator activationTokenGenerator;

    @Override
    @Transactional(propagation = REQUIRED)
    public User registerUser(RegDto regDto) {
        User user = new User().setName(regDto.name).setEmail(regDto.email).setFacebookId(regDto.facebookId).setLastSignInIpAddress(getRemoteAddr()).setActivationToken(activationTokenGenerator.generate());

        Object salt = saltSource.getSalt(new UserDetailsImpl(user));
        String encodedToken = passwordEncoder.encodePassword(regDto.token, salt);

        return userRepository.save(user.setToken(encodedToken));
    }
}
