package web.security.handler.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.persistence.domain.User;
import web.persistence.repository.UserRepository;
import web.security.credential.impl.UserDetailsImpl;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static common.util.RequestUtils.getRemoteAddr;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

// @author Titov Mykhaylo (titov) on 11.01.14 22:38
@Component
public class RememberMeSuccessfulHandler implements AuthenticationSuccessHandler {

    @Resource UserRepository userRepository;

    @Override
    @Transactional(propagation = REQUIRED)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        String requestURI = request.getRequestURI();
        if (principal instanceof UserDetailsImpl && requestURI.endsWith("/signin") ) {
            long userId = ((UserDetailsImpl) principal).getId();
            User user = userRepository.findOne(userId);
            updateLastLoginInfo(user);
        } else if (principal instanceof SocialUserDetails && requestURI.contains("/auth/")){
            String userName = ((SocialUserDetails) principal).getUserId();
            User user = userRepository.findByName(userName);
            updateLastLoginInfo(user);
        }
    }

    private void updateLastLoginInfo(User user) {
        userRepository.save(user.setLastSignInIpAddress(getRemoteAddr()).setLastSignInTimestamp(new Date()));
    }

}
