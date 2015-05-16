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
import web.signup.dto.RegDto;

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
        String prefix;
        if(api instanceof Facebook){
            prefix = RegDto.FACEBOOK_USER_NAME_PREFIX;
            if(signUp) {
                User userProfile = ((Facebook) api).userOperations().getUserProfile();
                facebookProfileRegService.registerUser(userProfile);
            }
        }else if(api instanceof Google){
            prefix = RegDto.GOOGLE_PLUS_USER_NAME_PREFIX;
            if(signUp) {
                Person googleProfile = ((Google) api).plusOperations().getGoogleProfile();
                googleProfileRegService.registerUser(googleProfile);
            }
        }else {
            log.error("Unknown social network api {}", api);
            throw new RuntimeException("Unknown social network api " + api);
        }

        return prefix + connection.getKey().getProviderUserId();
    }
}
