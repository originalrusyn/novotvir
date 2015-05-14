package web.security.social.google.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;
import web.persistence.domain.User;
import web.security.credential.impl.UserDetailsImpl;
import web.security.social.google.service.GoogleProfileRegService;
import web.signup.gplus.service.impl.UserGoogleRegServiceImpl;

import javax.annotation.Resource;

// @author Titov Mykhaylo (titov) on 01.05.15 16:21.
@Service
public class GoogleProfileRegServiceImpl implements GoogleProfileRegService{

    @Resource UserGoogleRegServiceImpl userGoogleRegService;

    @Override
    public UserDetails registerUser(Person person){
        User user = userGoogleRegService.registerUser(person);
        return new UserDetailsImpl(user);
    }
}
