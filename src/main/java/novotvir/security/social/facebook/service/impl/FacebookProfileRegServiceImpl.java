package novotvir.security.social.facebook.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import novotvir.persistence.domain.User;
import novotvir.security.credential.impl.UserDetailsImpl;
import novotvir.security.social.facebook.service.FacebookProfileRegService;
import novotvir.service.UserFacebookRegService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.FacebookProfile;

/**
 * @author Titov Mykhaylo (titov) on 17.03.14.
 */
@Slf4j
public class FacebookProfileRegServiceImpl implements FacebookProfileRegService {

    @Setter UserFacebookRegService userFacebookRegService;

    @Override
    public UserDetails registerUser(FacebookProfile facebookProfile){
        User user = userFacebookRegService.registerUser(facebookProfile);
        return new UserDetailsImpl(user);
    }
}
