package web.security.social.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import web.persistence.domain.Authority;
import web.persistence.domain.User;
import web.persistence.repository.UserRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

// @author: Mykhaylo Titov on 09.05.15 15:01.
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    @Resource UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        User user = userRepository.findByName(userId);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : user.authorities) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.role.name());
            grantedAuthorities.add(grantedAuthority);
        }
        return new SocialUser(user.name, user.token, grantedAuthorities);
    }
}
