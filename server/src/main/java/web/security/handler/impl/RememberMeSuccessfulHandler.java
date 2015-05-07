package web.security.handler.impl;

import lombok.Setter;
import web.persistence.domain.User;
import web.persistence.repository.UserRepository;
import web.security.credential.impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static common.util.RequestUtils.getRemoteAddr;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

// @author Titov Mykhaylo (titov) on 11.01.14 22:38
public class RememberMeSuccessfulHandler implements AuthenticationSuccessHandler {

    @Setter
    private UserRepository userRepository;

    @Override
    @Transactional(propagation = REQUIRED)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl){
            UserDetailsImpl userDetailsImpl=(UserDetailsImpl)principal;
            long userId = userDetailsImpl.getUserId();
            User user = userRepository.findOne(userId);

            String requestURI = request.getRequestURI();
            if (requestURI.endsWith("/signin") || requestURI.endsWith("/facebook_signin"))
                updateLastWebLoginInfo(user);
        }

    }

    private User updateLastWebLoginInfo(User user) {
        return userRepository.save(user.setLastSignInIpAddress(getRemoteAddr()).setLastWebSignInTimestamp(new Date()));
    }
}
