package web.security.social.google.service;

// @author Titov Mykhaylo (titov) on 01.05.15 16:16.
public interface GoogleRedirectUriService {
    String getGoogleRedirectUriWithSignUpParam(boolean signUp);
}
