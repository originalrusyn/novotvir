package common.security.service;

// @author Titov Mykhaylo (titov) on 05.05.2014.
public interface CustomTokenBasedRememberMeService {

    String getRememberMeToken(String userName, String password);
}
