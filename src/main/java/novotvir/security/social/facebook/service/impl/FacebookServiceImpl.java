package novotvir.security.social.facebook.service.impl;

import lombok.Setter;
import novotvir.persistence.domain.User;
import novotvir.security.social.facebook.service.FacebookService;
import novotvir.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.support.URIBuilder;

import static novotvir.utils.RequestUtils.getRemoteAddr;
import static org.springframework.social.oauth2.GrantType.AUTHORIZATION_CODE;

/**
 * @author Titov Mykhaylo (titov) on 13.03.14.
 */
public class FacebookServiceImpl implements FacebookService{
    public static final String SIGN_UP = "signUp";
    @Setter
    FacebookConnectionFactory facebookConnectionFactory;
    @Setter
    UserService userService;
    @Setter
    String facebookRedirectUri;

    @Override
    public User registerUser(FacebookProfile facebookProfile) {
        String facebookId = facebookProfile.getId();
        String email = facebookProfile.getEmail();
        String token = RandomStringUtils.random(6);
        return userService.save(new User().setFacebookId(facebookId).setName(facebookId).setEmail(email).setToken(token).setLastSignInIpAddress(getRemoteAddr()));
    }

    @Override
    public String getFacebookBuildAuthorizationUrl(boolean signUp) {
        String redirectUri = getFacebookRedirectUtilWithSignUpParam(signUp);
        OAuth2Operations oAuthOperations = facebookConnectionFactory.getOAuthOperations();
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(redirectUri);
        parameters.setScope("email");
        return oAuthOperations.buildAuthorizeUrl(AUTHORIZATION_CODE, parameters);
    }

    @Override
    public FacebookProfile getFacebookProfile(String code, boolean signUp){
        String redirectUri = getFacebookRedirectUtilWithSignUpParam(signUp);
        OAuth2Operations oAuthOperations = facebookConnectionFactory.getOAuthOperations();
        AccessGrant accessGrant = oAuthOperations.exchangeForAccess(code, redirectUri, null);
        Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
        Facebook facebook = connection.getApi();
        return facebook.userOperations().getUserProfile();
    }

    private String getFacebookRedirectUtilWithSignUpParam(boolean signUp) {
        return URIBuilder.fromUri(facebookRedirectUri).queryParam(SIGN_UP, String.valueOf(signUp)).build().toString();
    }
}
