package novotvir.security.social.google.service;

import org.springframework.social.google.api.plus.Person;

// @author: m on 01.05.15 16:01.
public interface GoogleProfileService {
    Person getGoogleProfile(String code, boolean signUp);
}
