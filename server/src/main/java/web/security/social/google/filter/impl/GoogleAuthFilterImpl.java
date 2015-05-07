package web.security.social.google.filter.impl;

import lombok.Setter;
import web.security.social.google.service.GoogleAuthUrlService;
import web.security.social.google.service.GoogleProfileService;
import web.security.social.google.token.impl.GoogleAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.social.google.api.plus.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static web.security.social.facebook.service.impl.FacebookRedirectUriServiceImpl.SIGN_UP;

// @author Titov Mykhaylo (titov) on 01.05.15 15:54.
public class GoogleAuthFilterImpl extends AbstractAuthenticationProcessingFilter {

    @Setter GoogleAuthUrlService googleAuthUrlService;
    @Setter GoogleProfileService googleProfileService;

    protected GoogleAuthFilterImpl(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException,
            ServletException {
        String code = request.getParameter("code");
        boolean signUp = Boolean.parseBoolean(request.getParameter(SIGN_UP));
        Authentication authentication = null;
        if (isNull(code)) {
            String authorizeUrl = googleAuthUrlService.getGoogleAuthorizationUrl(signUp);
            response.sendRedirect(authorizeUrl);
        } else {
            Person person = googleProfileService.getGoogleProfile(code, signUp);
            authentication = new GoogleAuthenticationToken(person, signUp);

            AuthenticationManager authenticationManager = getAuthenticationManager();
            authentication = authenticationManager.authenticate(authentication);
        }
        return authentication;
    }
}
