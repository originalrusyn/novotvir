package novotvir.service;

import novotvir.persistence.domain.User;
import org.springframework.social.google.api.plus.Person;

// @author: m on 01.05.15 16:25.
public interface UserGoogleRegService {
    User registerUser(Person person);
}
