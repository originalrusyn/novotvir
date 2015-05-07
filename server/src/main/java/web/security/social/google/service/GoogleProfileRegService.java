package web.security.social.google.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.google.api.plus.Person;

// @author Titov Mykhaylo (titov) on 01.05.15 16:21.
public interface GoogleProfileRegService {
    UserDetails registerUser(Person person);
}
