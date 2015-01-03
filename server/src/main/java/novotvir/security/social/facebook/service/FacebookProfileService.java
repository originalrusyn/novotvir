package novotvir.security.social.facebook.service;

import org.springframework.social.facebook.api.FacebookProfile;

// @author Titov Mykhaylo (titov) on 07.05.2014.
public interface FacebookProfileService {
    FacebookProfile getFacebookProfile(String code, boolean signUp);
}
