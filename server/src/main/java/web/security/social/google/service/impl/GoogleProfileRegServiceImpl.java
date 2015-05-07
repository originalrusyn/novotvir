package web.security.social.google.service.impl;

import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.google.api.plus.Person;
import web.persistence.domain.User;
import web.security.credential.impl.UserDetailsImpl;
import web.security.social.google.service.GoogleProfileRegService;
import web.signup.gplus.service.impl.UserGoogleRegServiceImpl;

// @author Titov Mykhaylo (titov) on 01.05.15 16:21.
public class GoogleProfileRegServiceImpl implements GoogleProfileRegService{

    @Setter UserGoogleRegServiceImpl userGoogleRegService;

    @Override
    public UserDetails registerUser(Person person){
        User user = userGoogleRegService.registerUser(person);
        return new UserDetailsImpl(user);
    }
}
