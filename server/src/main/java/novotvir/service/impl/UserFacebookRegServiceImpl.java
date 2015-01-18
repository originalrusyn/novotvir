package novotvir.service.impl;

import novotvir.dto.RegDto;
import novotvir.persistence.domain.User;
import novotvir.service.UserFacebookRegService;
import novotvir.service.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Service;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
@Service("userFacebookRegService")
public class UserFacebookRegServiceImpl implements UserFacebookRegService {

    @Autowired UserRegService userRegService;

    @Override
    public User registerUser(FacebookProfile facebookProfile) {
        return userRegService.registerUser(RegDto.getInstance(facebookProfile));
    }
}
