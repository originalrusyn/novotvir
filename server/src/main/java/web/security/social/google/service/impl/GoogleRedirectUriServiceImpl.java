package web.security.social.google.service.impl;

import lombok.Setter;
import web.security.social.google.service.GoogleRedirectUriService;

import static org.springframework.social.support.URIBuilder.fromUri;

// @author Titov Mykhaylo (titov) on 01.05.15 16:17.
public class GoogleRedirectUriServiceImpl implements GoogleRedirectUriService {
    public static final String SIGN_UP = "signUp";

    @Setter String googleRedirectUri;

    @Override
    public String getGoogleRedirectUriWithSignUpParam(boolean signUp) {
        //return fromUri(getServerURL() + googleRedirectUri).queryParam(SIGN_UP, String.valueOf(signUp)).build().toString();
        return fromUri("http://localhost:8080/novotvir" + googleRedirectUri).queryParam(SIGN_UP, String.valueOf(signUp)).build().toString();
    }
}
