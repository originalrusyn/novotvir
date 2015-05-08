package web.signup.facebook.service.impl;

import org.springframework.stereotype.Service;
import web.persistence.domain.User;
import web.signup.dto.RegDto;
import web.signup.service.UserRegService;

import javax.annotation.Resource;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
@Service("userFacebookRegService")
public class UserFacebookRegServiceImpl {

    @Resource UserRegService userRegService;

    public User registerUser(org.springframework.social.facebook.api.User facebookProfile) {
        return userRegService.registerUser(RegDto.getInstance(facebookProfile));
    }
}
