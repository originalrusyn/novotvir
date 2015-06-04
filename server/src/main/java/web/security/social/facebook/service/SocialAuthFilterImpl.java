package web.security.social.facebook.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

// @author: Mykhaylo Titov on 04.06.15 23:03.
public class SocialAuthFilterImpl extends SocialAuthenticationFilter {

    public SocialAuthFilterImpl(AuthenticationManager authManager, UserIdSource userIdSource, UsersConnectionRepository usersConnectionRepository, SocialAuthenticationServiceLocator authServiceLocator) {
        super(authManager, userIdSource, usersConnectionRepository, authServiceLocator);
    }

    @Override
    protected boolean detectRejection(HttpServletRequest request) {
        String accessToken = request.getParameter("accessToken");
        return !StringUtils.hasText(accessToken) && super.detectRejection(request);
    }
}
