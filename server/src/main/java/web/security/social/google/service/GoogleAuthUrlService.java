package web.security.social.google.service;

// @author Titov Mykhaylo (titov) on 01.05.15 15:57.
public interface GoogleAuthUrlService {
    String getGoogleAuthorizationUrl(boolean signUp);
}
