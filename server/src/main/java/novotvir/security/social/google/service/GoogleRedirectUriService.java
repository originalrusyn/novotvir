package novotvir.security.social.google.service;

// @author: m on 01.05.15 16:16.
public interface GoogleRedirectUriService {
    String getGoogleRedirectUriWithSignUpParam(boolean signUp);
}
