package web.security.social.google.service.impl;

import lombok.Setter;
import web.security.social.google.service.GoogleProfileService;
import web.security.social.google.service.GoogleRedirectUriService;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

// @author Titov Mykhaylo (titov) on 01.05.15 16:34.
public class GoogleProfileServiceImpl implements GoogleProfileService {

    @Setter GoogleConnectionFactory googleConnectionFactory;
    @Setter GoogleRedirectUriService googleRedirectUriService;

    @Override
    public Person getGoogleProfile(String code, boolean signUp) {
        String redirectUri =  googleRedirectUriService.getGoogleRedirectUriWithSignUpParam(signUp);
        OAuth2Operations oAuthOperations = googleConnectionFactory.getOAuthOperations();
        AccessGrant accessGrant = oAuthOperations.exchangeForAccess(code, redirectUri, null);
        Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
        Google google = connection.getApi();
        return google.plusOperations().getGoogleProfile();
    }
}
