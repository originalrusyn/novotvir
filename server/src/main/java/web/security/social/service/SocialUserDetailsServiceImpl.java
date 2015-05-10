package web.security.social.service;

import common.enums.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import web.persistence.repository.UserRepository;

import javax.annotation.Resource;
import java.util.Collections;

// @author: Mykhaylo Titov on 09.05.15 15:01.
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    @Resource UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        return new SocialUser("gg", "ggg", Collections.singletonList(new SimpleGrantedAuthority(Role.USER.name())));
    }
}
