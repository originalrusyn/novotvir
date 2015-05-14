package web.security.social.facebook.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import web.persistence.domain.User;
import web.security.credential.impl.UserDetailsImpl;
import web.security.social.facebook.service.FacebookProfileRegService;
import web.signup.facebook.service.impl.UserFacebookRegServiceImpl;

import javax.annotation.Resource;

// @author Titov Mykhaylo (titov) on 17.03.14.
@Slf4j
@Service
public class FacebookProfileRegServiceImpl implements FacebookProfileRegService {

    @Resource UserFacebookRegServiceImpl userFacebookRegService;

    @Override
    public UserDetails registerUser(org.springframework.social.facebook.api.User facebookProfile){
        User user = userFacebookRegService.registerUser(facebookProfile);
        return new UserDetailsImpl(user);
    }
}
