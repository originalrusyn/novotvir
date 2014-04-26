package novotvir.security.social.facebook.filter.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import novotvir.security.social.facebook.service.impl.FacebookServiceImpl;
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
import static novotvir.security.social.facebook.service.impl.FacebookServiceImpl.SIGN_UP;

/**
 * @author Titov Mykhaylo (titov) on 24.04.2014.
 */

@Slf4j
public class FacebookAuthFilterImpl extends AbstractAuthenticationProcessingFilter {
    @Setter
    FacebookServiceImpl facebookService;

    protected FacebookAuthFilterImpl(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException,
            ServletException {
        log.debug("input parameters request, response: [{}], [{}]", request, response);

        Authentication authentication = null;
        String code = request.getParameter("code");
        boolean signUp = Boolean.valueOf(request.getParameter(SIGN_UP));
        String buildAuthorizeUrl = facebookService.getFacebookBuildAuthorizationUrl(signUp);
        if (isNull(code)) {
            response.sendRedirect(buildAuthorizeUrl);
        } else {
            FacebookProfile facebookProfile = facebookService.getFacebookProfile(code, signUp);
            authentication = new FacebookAuthenticationToken(facebookProfile, signUp);

            AuthenticationManager authenticationManager = getAuthenticationManager();
            authentication = authenticationManager.authenticate(authentication);
        }

        log.debug("Output parameter authentication=[{}]", authentication);
        return authentication;
    }
}
