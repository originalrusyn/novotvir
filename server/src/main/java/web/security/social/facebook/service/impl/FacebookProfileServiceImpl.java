package web.security.social.facebook.service.impl;

import lombok.Setter;
import org.springframework.social.facebook.api.User;
import web.security.social.facebook.service.FacebookProfileService;
import web.security.social.facebook.service.FacebookRedirectUriService;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

// @author Titov Mykhaylo (titov) on 07.05.2014.
public class FacebookProfileServiceImpl implements FacebookProfileService {

    @Setter FacebookConnectionFactory facebookConnectionFactory;
    @Setter FacebookRedirectUriService facebookRedirectUriService;

    @Override
    public User getFacebookProfile(String code, boolean signUp){
        String redirectUri = facebookRedirectUriService.getFacebookRedirectUriWithSignUpParam(signUp);
        OAuth2Operations oAuthOperations = facebookConnectionFactory.getOAuthOperations();
        AccessGrant accessGrant = oAuthOperations.exchangeForAccess(code, redirectUri, null);
        Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
        Facebook facebook = connection.getApi();
        return facebook.userOperations().getUserProfile();
    }
}
