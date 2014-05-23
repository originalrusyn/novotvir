package novotvir.security.social.facebook.service;

import novotvir.persistence.domain.User;
import org.springframework.social.facebook.api.FacebookProfile;

/**
 * @author Titov Mykhaylo (titov) on 17.03.14.
 */
public interface FacebookRedirectUriService {
    String getFacebookRedirectUriWithSignUpParam(boolean signUp);
}
