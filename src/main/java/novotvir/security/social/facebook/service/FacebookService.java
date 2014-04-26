package novotvir.security.social.facebook.service;

import novotvir.persistence.domain.User;
import org.springframework.social.facebook.api.FacebookProfile;

/**
 * @author Titov Mykhaylo (titov) on 17.03.14.
 */
public interface FacebookService {

    User registerUser(FacebookProfile facebookProfile);

    String getFacebookBuildAuthorizationUrl(boolean signUp);

    FacebookProfile getFacebookProfile(String code, boolean signUp);
}
