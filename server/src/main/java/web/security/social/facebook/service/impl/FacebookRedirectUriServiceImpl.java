package web.security.social.facebook.service.impl;

import lombok.Setter;
import web.security.social.facebook.service.FacebookRedirectUriService;

import static common.util.RequestUtils.getServerURL;
import static org.springframework.social.support.URIBuilder.fromUri;

// @author Titov Mykhaylo (titov) on 13.03.14.
public class FacebookRedirectUriServiceImpl implements FacebookRedirectUriService {
    public static final String SIGN_UP = "signUp";

    @Setter String facebookRedirectUri;

    public String getFacebookRedirectUriWithSignUpParam(boolean signUp) {
        return fromUri(getServerURL() + facebookRedirectUri).queryParam(SIGN_UP, String.valueOf(signUp)).build().toString();
    }
}
