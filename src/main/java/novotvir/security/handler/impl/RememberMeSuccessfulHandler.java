package novotvir.security.handler.impl;

import lombok.Setter;
import novotvir.persistence.domain.User;
import novotvir.security.credential.impl.UserDetailsImpl;
import novotvir.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Titov Mykhaylo (titov)
 *         11.01.14 22:38
 */
public class RememberMeSuccessfulHandler implements AuthenticationSuccessHandler {

    @Setter
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl){
            UserDetailsImpl userDetailsImpl=(UserDetailsImpl)principal;
            long userId = userDetailsImpl.getUserId();
            User user = userService.findOne(userId);

            String requestURI = request.getRequestURI();
            if (requestURI.endsWith("/signin") || requestURI.endsWith("/facebook_signin"))
                userService.updateLastWebLoginInfo(user);
        }

    }
}
