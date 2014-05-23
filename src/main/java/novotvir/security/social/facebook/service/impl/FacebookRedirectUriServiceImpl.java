package novotvir.security.social.facebook.service.impl;

import lombok.Setter;
import novotvir.security.social.facebook.service.FacebookRedirectUriService;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import static org.springframework.social.oauth2.GrantType.AUTHORIZATION_CODE;
import static org.springframework.social.support.URIBuilder.fromUri;

/**
 * @author Titov Mykhaylo (titov) on 13.03.14.
 */
public class FacebookRedirectUriServiceImpl implements FacebookRedirectUriService {
    public static final String SIGN_UP = "signUp";

    @Setter String facebookRedirectUri;

    public String getFacebookRedirectUriWithSignUpParam(boolean signUp) {
        return fromUri(facebookRedirectUri).queryParam(SIGN_UP, String.valueOf(signUp)).build().toString();
    }
}
