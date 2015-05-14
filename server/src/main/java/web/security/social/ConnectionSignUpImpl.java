package web.security.social;

import common.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import web.security.social.facebook.service.FacebookProfileRegService;
import web.security.social.google.service.GoogleProfileRegService;

import javax.annotation.Resource;

// @author: Mykhaylo Titov on 09.05.15 15:40.
@Slf4j
public class ConnectionSignUpImpl implements ConnectionSignUp {

    public static final String SIGN_UP = "signUp";
    @Resource FacebookProfileRegService facebookProfileRegService;
    @Resource GoogleProfileRegService googleProfileRegService;

    @Override
    public String execute(Connection<?> connection) {

        boolean signUp = Boolean.parseBoolean(RequestUtils.getHttpServletRequest().getParameter(SIGN_UP));

        Object api = connection.getApi();
        if(signUp){
            if(api instanceof Facebook){
                User userProfile = ((Facebook) api).userOperations().getUserProfile();
                facebookProfileRegService.registerUser(userProfile);
            }else if(api instanceof Google){
                Person googleProfile = ((Google) api).plusOperations().getGoogleProfile();
                googleProfileRegService.registerUser(googleProfile);
            }else {
                log.error("Unknown api {}", api);
                throw new RuntimeException("Unknown api " + api);
            }
        }

        return connection.getKey().getProviderUserId();
    }
}
