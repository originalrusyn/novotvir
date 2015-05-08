package web.security.social.facebook.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import web.persistence.domain.User;
import web.security.credential.impl.UserDetailsImpl;
import web.security.social.facebook.service.FacebookProfileRegService;
import web.signup.facebook.service.impl.UserFacebookRegServiceImpl;

// @author Titov Mykhaylo (titov) on 17.03.14.
@Slf4j
public class FacebookProfileRegServiceImpl implements FacebookProfileRegService {

    @Setter UserFacebookRegServiceImpl userFacebookRegService;

    @Override
    public UserDetails registerUser(org.springframework.social.facebook.api.User facebookProfile){
        User user = userFacebookRegService.registerUser(facebookProfile);
        return new UserDetailsImpl(user);
    }
}
