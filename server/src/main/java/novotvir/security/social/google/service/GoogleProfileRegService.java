package novotvir.security.social.google.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.google.api.plus.Person;

// @author: m on 01.05.15 16:21.
public interface GoogleProfileRegService {
    UserDetails registerUser(Person person);
}
