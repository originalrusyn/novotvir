package web.security.social.facebook.service;

// @author Titov Mykhaylo (titov) on 17.03.14.
public interface FacebookRedirectUriService {
    String getFacebookRedirectUriWithSignUpParam(boolean signUp);
}
