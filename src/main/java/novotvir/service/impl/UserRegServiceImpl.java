package novotvir.service.impl;

import novotvir.dto.RegDto;
import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.security.credential.impl.UserDetailsImpl;
import novotvir.service.UserRegService;
import novotvir.utils.generator.ActivationTokenGenerator;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static novotvir.utils.RequestUtils.getRemoteAddr;
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
