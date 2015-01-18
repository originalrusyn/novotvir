package novotvir.security.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.security.credential.impl.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

// @author Titov Mykhaylo (titov) on 11.01.14 20:22
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Setter
    protected UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByName(userName);

        if (isNull(user))
            throw new UsernameNotFoundException("Couldn't find user with userName [" + userName + "] in the DB");

        return new UserDetailsImpl(user);
    }

}

