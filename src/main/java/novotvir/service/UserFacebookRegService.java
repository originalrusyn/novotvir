package novotvir.service;

import novotvir.persistence.domain.User;
import org.springframework.social.facebook.api.FacebookProfile;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
public interface UserFacebookRegService {
    User registerUser(FacebookProfile facebookProfile);
}
