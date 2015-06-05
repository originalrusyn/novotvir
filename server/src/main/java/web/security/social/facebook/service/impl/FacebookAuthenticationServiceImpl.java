package web.security.social.facebook.service.impl;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.security.FacebookAuthenticationService;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static web.security.social.SocialAuthFilterImpl.ACCESS_TOKEN;

// @author: Mykhaylo Titov on 04.06.15 22:20.
public class FacebookAuthenticationServiceImpl extends FacebookAuthenticationService {

    public FacebookAuthenticationServiceImpl(String apiKey, String appSecret) {
        super(apiKey, appSecret);
    }

    @Override
    public SocialAuthenticationToken getAuthToken(HttpServletRequest request, HttpServletResponse response) throws SocialAuthenticationRedirectException {
        String accessToken = request.getParameter(ACCESS_TOKEN);
        if (StringUtils.hasText(accessToken)) {
            AccessGrant accessGrant = new AccessGrant(accessToken);
            Connection<Facebook> connection = getConnectionFactory().createConnection(accessGrant);
            return new SocialAuthenticationToken(connection, null);
        } else {
            return super.getAuthToken(request, response);
        }
    }

}
