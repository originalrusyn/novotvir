package common.security.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import common.security.service.CustomTokenBasedRememberMeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.springframework.util.DigestUtils.md5DigestAsHex;

// @author Titov Mykhaylo (titov) on 11.01.14 19:23
@Slf4j
public class CustomTokenBasedRememberMeServicesImpl extends TokenBasedRememberMeServices implements CustomTokenBasedRememberMeService{

    @Setter
    private AuthenticationSuccessHandler successHandler;

    public CustomTokenBasedRememberMeServicesImpl(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    protected String retrieveUserName(Authentication authentication) {
        String userName = super.retrieveUserName(authentication);
        String encodedUserName = null;
        if (isNotBlank(userName)) {
            encodedUserName = getEncodedUserName(userName);
        }
        return encodedUserName;
    }

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        try {
            super.onLoginSuccess(request, response, successfulAuthentication);
            if (nonNull(successHandler))
                successHandler.onAuthenticationSuccess(request, response, successfulAuthentication);
        } catch (IOException | ServletException e) {
            log.error("Couldn't login", e);
        }
    }

    @Override
    protected String makeTokenSignature(long tokenExpiryTimeMillis, String username, String password) {
        return md5DigestAsHex((getEncodedUserName(username) + ":" + tokenExpiryTimeMillis + ":" + password + ":" + getKey()).getBytes(Charset.forName("UTF-8")));
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
        try {
            cookieTokens[0] = getDecodedUserName(cookieTokens[0]);
            log.debug("Process auto login with cookie auth tokens: [{}]", cookieTokens);
            return super.processAutoLoginCookie(cookieTokens, request, response);
        } catch (Exception e) {
            log.error("Couldn't auto login", e);
            throw new InvalidCookieException(e.getMessage());
        }
    }

    @Override
    protected String extractRememberMeCookie(HttpServletRequest httpServletRequest) {
        String rememberMeCookie = httpServletRequest.getHeader(getCookieName());
        if (isNull(rememberMeCookie)) {
            rememberMeCookie = super.extractRememberMeCookie(httpServletRequest);
        }
        return rememberMeCookie;
    }

    @Override
    public String getRememberMeToken(String userName, String password) {
        String encodedUserName = getEncodedUserName(userName);

        long tokenExpirationMillis = getTokenExpirationMillis();
        String tokenSignature = makeTokenSignature(tokenExpirationMillis, encodedUserName, password);

        return encodeCookie(new String[] {encodedUserName, Long.toString(tokenExpirationMillis), tokenSignature});
    }

    private long getTokenExpirationMillis(){
        int tokenLifetime = getTokenValiditySeconds();
        return System.currentTimeMillis() + 1000L* (tokenLifetime < 0 ? TWO_WEEKS_S : tokenLifetime);
    }

    public String getEncodedUserName(String userName) {
        return userName.replace(':', '|');
    }

    protected String getDecodedUserName(String userName) {
        return userName.replace('|', ':');
    }
}
