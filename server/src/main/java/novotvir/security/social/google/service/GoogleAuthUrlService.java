package novotvir.security.social.google.service;

// @author: m on 01.05.15 15:57.
public interface GoogleAuthUrlService {
    String getGoogleAuthorizationUrl(boolean signUp);
}
