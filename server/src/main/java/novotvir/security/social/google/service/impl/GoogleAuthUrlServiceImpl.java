package novotvir.security.social.google.service.impl;

import lombok.Setter;
import novotvir.security.social.google.service.GoogleAuthUrlService;
import novotvir.security.social.google.service.GoogleRedirectUriService;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import static org.springframework.social.oauth2.GrantType.AUTHORIZATION_CODE;

// @author: m on 01.05.15 16:13.
public class GoogleAuthUrlServiceImpl implements GoogleAuthUrlService{
    @Setter GoogleConnectionFactory googleConnectionFactory;
    @Setter GoogleRedirectUriService googleRedirectUriService;

    @Override
    public String getGoogleAuthorizationUrl(boolean signUp) {
        String redirectUri = googleRedirectUriService.getGoogleRedirectUriWithSignUpParam(signUp);
        OAuth2Operations oAuthOperations = googleConnectionFactory.getOAuthOperations();
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(redirectUri);
        parameters.setScope("email");
        return oAuthOperations.buildAuthorizeUrl(AUTHORIZATION_CODE, parameters);
    }
}
