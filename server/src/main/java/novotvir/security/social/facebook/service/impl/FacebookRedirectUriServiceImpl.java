package novotvir.security.social.facebook.service.impl;

import lombok.Setter;
import novotvir.security.social.facebook.service.FacebookRedirectUriService;

import static novotvir.utils.RequestUtils.getServerURL;
import static org.springframework.social.support.URIBuilder.fromUri;

// @author Titov Mykhaylo (titov) on 13.03.14.
public class FacebookRedirectUriServiceImpl implements FacebookRedirectUriService {
    public static final String SIGN_UP = "signUp";

    @Setter String facebookRedirectUri;

    public String getFacebookRedirectUriWithSignUpParam(boolean signUp) {
        return fromUri(getServerURL() + facebookRedirectUri).queryParam(SIGN_UP, String.valueOf(signUp)).build().toString();
    }
}
