package novotvir.security.social.facebook.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import novotvir.persistence.domain.User;
import novotvir.security.credential.impl.UserDetailsImpl;
import novotvir.security.service.impl.UserDetailsServiceImpl;
import novotvir.security.social.facebook.service.FacebookService;
import novotvir.security.social.facebook.service.FacebookUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.FacebookProfile;

/**
 * @author Titov Mykhaylo (titov) on 17.03.14.
 */
@Slf4j
public class FacebookUserDetailsServiceImpl extends UserDetailsServiceImpl implements FacebookUserDetailsService{

    @Setter
    protected FacebookService facebookService;

    @Override
    public UserDetails registerUser(FacebookProfile facebookProfile){
        User user = facebookService.registerUser(facebookProfile);
        return new UserDetailsImpl(user);
    }
}
