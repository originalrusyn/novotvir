package admin.security.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import admin.persistence.domain.Admin;
import admin.persistence.repository.AdminRepository;
import admin.security.credential.impl.AdminDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Objects.isNull;

// @author: Mykhaylo Titov on 06.07.14 22:19.
@Slf4j
public class AdminDetailsServiceImpl implements UserDetailsService {

    @Setter AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(userName);

        if (isNull(admin)) {
            throw new UsernameNotFoundException("Couldn't find user with userName [" + userName + "] in the DB");
        }

        return new AdminDetailsImpl(admin);
    }
}
