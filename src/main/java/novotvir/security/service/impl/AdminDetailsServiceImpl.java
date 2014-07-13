package novotvir.security.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import novotvir.persistence.domain.Admin;
import novotvir.persistence.repository.AdminRepository;
import novotvir.security.credential.impl.AdminDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Objects.isNull;

/**
 * Created by Mykaylo Titov on 06.07.14.
 */
@Slf4j
public class AdminDetailsServiceImpl implements UserDetailsService {

    @Setter AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.debug("input parameters userName: [{}]", userName);

        Admin admin = adminRepository.findByEmail(userName);

        if (isNull(admin))
            throw new UsernameNotFoundException("Couldn't find user with userName [" + userName + "] in the DB");

        UserDetails userDetails = new AdminDetailsImpl(admin);

        log.debug("Output parameter userDetails=[{}]", userDetails);
        return userDetails;
    }
}
