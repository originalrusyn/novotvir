package novotvir.security.social.facebook.service.impl;

import lombok.Setter;
import novotvir.security.social.facebook.service.FacebookAuthUrlService;
import novotvir.security.social.facebook.service.FacebookRedirectUriService;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import static org.springframework.social.oauth2.GrantType.AUTHORIZATION_CODE;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
public class FacebookAuthUrlServiceImpl implements FacebookAuthUrlService {
    @Setter FacebookConnectionFactory facebookConnectionFactory;
    @Setter FacebookRedirectUriService facebookRedirectUriService;

    @Override
    public String getFacebookAuthorizationUrl(boolean signUp) {
        String redirectUri = facebookRedirectUriService.getFacebookRedirectUriWithSignUpParam(signUp);
        OAuth2Operations oAuthOperations = facebookConnectionFactory.getOAuthOperations();
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(redirectUri);
        parameters.setScope("email");
        return oAuthOperations.buildAuthorizeUrl(AUTHORIZATION_CODE, parameters);
    }
}
