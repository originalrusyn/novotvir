package web.signup.service.impl;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.persistence.domain.EmailAddress;
import web.persistence.domain.User;
import web.persistence.repository.EmailAddressRepository;
import web.persistence.repository.UserRepository;
import web.security.credential.impl.UserDetailsImpl;
import web.signup.dto.RegDto;
import web.signup.email.generator.ActivationTokenGenerator;
import web.signup.service.UserRegService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

// @author Titov Mykhaylo (titov) on 07.05.2014.
@Service("userRegService")
public class UserRegServiceImpl implements UserRegService {

    @Resource EmailAddressRepository emailAddressRepository;
    @Resource UserRepository userRepository;
    @Resource SaltSource saltSource;
    @Resource PasswordEncoder passwordEncoder;
    @Resource ActivationTokenGenerator activationTokenGenerator;

    @Override
    @Transactional(propagation = REQUIRED)
    public User registerUser(RegDto regDto) {
        User user = new User(regDto.getName(), regDto.getToken()).setActivated(regDto.isActivated());
        if(!user.isActivated()){
            user.setActivationToken(activationTokenGenerator.generate());
        }

        Object salt = saltSource.getSalt(new UserDetailsImpl(user));
        String encodedToken = passwordEncoder.encodePassword(regDto.getToken(), salt);

        user = userRepository.save(user.setToken(encodedToken));

        if (regDto.getEmail().isPresent()) {
            EmailAddress emailAddress = emailAddressRepository.save(new EmailAddress().setEmail(regDto.getEmail().get()).setUser(user));

            List<EmailAddress> emailAddresses = new ArrayList<>();
            emailAddresses.add(emailAddress);
            return userRepository.save(user.setPrimaryEmailAddress(emailAddress).setEmailAddresses(emailAddresses));
        }

        return user;

    }
}
