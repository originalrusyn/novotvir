package novotvir.security.social.facebook.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.FacebookProfile;

// @author Titov Mykhaylo (titov) on 17.03.14.
public interface FacebookProfileRegService {
    UserDetails registerUser(FacebookProfile facebookProfile);
}
