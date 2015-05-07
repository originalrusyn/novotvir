package web.signup.gplus.service.impl;

import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;
import web.persistence.domain.User;
import web.signup.dto.RegDto;
import web.signup.service.UserRegService;

import javax.annotation.Resource;

// @author Titov Mykhaylo (titov) on 01.05.15 16:27.
@Service("userGoogleRegService")
public class UserGoogleRegServiceImpl {
    @Resource UserRegService userRegService;

    public User registerUser(Person person) {
        return userRegService.registerUser(RegDto.getInstance(person));
    }
}
