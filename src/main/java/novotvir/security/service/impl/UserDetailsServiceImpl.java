package novotvir.security.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import novotvir.persistence.domain.User;
import novotvir.security.credential.impl.UserDetailsImpl;
import novotvir.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Objects.isNull;

/**
 * @author Titov Mykhaylo (titov)
 *         11.01.14 20:22
 */
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Setter
    protected UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.debug("input parameters userName: [{}]", userName);

        User user = userService.findByName(userName);

        if (isNull(user))
            throw new UsernameNotFoundException("Couldn't find user with userName [" + userName + "] in the DB");

        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);

        log.debug("Output parameter userDetailsImpl=[{}]", userDetailsImpl);
        return userDetailsImpl;
    }

}

