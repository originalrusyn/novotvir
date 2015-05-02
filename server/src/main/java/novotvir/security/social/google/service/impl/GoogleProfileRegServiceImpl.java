package novotvir.security.social.google.service.impl;

import lombok.Setter;
import novotvir.persistence.domain.User;
import novotvir.security.credential.impl.UserDetailsImpl;
import novotvir.security.social.google.service.GoogleProfileRegService;
import novotvir.service.UserGoogleRegService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.google.api.plus.Person;

// @author: m on 01.05.15 16:21.
public class GoogleProfileRegServiceImpl implements GoogleProfileRegService{

    @Setter UserGoogleRegService userGoogleRegService;

    @Override
    public UserDetails registerUser(Person person){
        User user = userGoogleRegService.registerUser(person);
        return new UserDetailsImpl(user);
    }
}
