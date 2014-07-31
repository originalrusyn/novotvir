package novotvir.security.social.facebook.filter.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import novotvir.security.social.facebook.service.FacebookAuthUrlService;
import novotvir.security.social.facebook.service.FacebookProfileService;
import novotvir.security.social.facebook.token.impl.FacebookAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.social.facebook.api.FacebookProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static novotvir.security.social.facebook.service.impl.FacebookRedirectUriServiceImpl.SIGN_UP;

/**
 * @author Titov Mykhaylo (titov) on 24.04.2014.
 */

@Slf4j
public class FacebookAuthFilterImpl extends AbstractAuthenticationProcessingFilter {

    @Setter FacebookAuthUrlService facebookAuthUrlService;
    @Setter FacebookProfileService facebookProfileService;

    protected FacebookAuthFilterImpl(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException,
            ServletException {
        String code = request.getParameter("code");
        boolean signUp = Boolean.parseBoolean(request.getParameter(SIGN_UP));
        String authorizeUrl = facebookAuthUrlService.getFacebookAuthorizationUrl(signUp);
        Authentication authentication = null;
        if (isNull(code)) {
            response.sendRedirect(authorizeUrl);
        } else {
            FacebookProfile facebookProfile = facebookProfileService.getFacebookProfile(code, signUp);
            authentication = new FacebookAuthenticationToken(facebookProfile, signUp);

            AuthenticationManager authenticationManager = getAuthenticationManager();
            authentication = authenticationManager.authenticate(authentication);
        }
        return authentication;
    }
}
