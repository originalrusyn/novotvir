package novotvir.security.social.facebook.service;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
public interface FacebookAuthUrlService {
    String getFacebookAuthorizationUrl(boolean signUp);
}
