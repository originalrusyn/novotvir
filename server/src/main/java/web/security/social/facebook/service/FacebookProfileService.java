package web.security.social.facebook.service;

import org.springframework.social.facebook.api.User;

// @author Titov Mykhaylo (titov) on 07.05.2014.
public interface FacebookProfileService {
    User getFacebookProfile(String code, boolean signUp);
}
