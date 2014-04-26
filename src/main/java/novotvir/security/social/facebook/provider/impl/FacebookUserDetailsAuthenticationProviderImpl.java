package novotvir.security.social.facebook.provider.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import novotvir.security.social.facebook.service.FacebookUserDetailsService;
import novotvir.security.social.facebook.token.impl.FacebookAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.FacebookProfile;

import java.util.Collection;

/**
 * @author Titov Mykhaylo (titov)
 *         11.01.14 20:09
 */
@Slf4j
public class FacebookUserDetailsAuthenticationProviderImpl implements AuthenticationProvider {

    @Setter
    private GrantedAuthoritiesMapper authoritiesMapper;
    @Setter
    private FacebookUserDetailsService facebookUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("input parameters authentication: [{}]", authentication);
        FacebookAuthenticationToken facebookAuthenticationToken = (FacebookAuthenticationToken) authentication;

        final FacebookProfile facebookProfile = facebookAuthenticationToken.getFacebookProfile();
        boolean signUp = facebookAuthenticationToken.isSignUp();

        UserDetails userDetails;
        if(signUp){
            userDetails = facebookUserDetailsService.registerUser(facebookProfile);
        }else{
            userDetails = facebookUserDetailsService.loadUserByUsername(facebookProfile.getId());
        }

        Collection<? extends GrantedAuthority> grantedAuthorities = authoritiesMapper.mapAuthorities(userDetails.getAuthorities());

        facebookAuthenticationToken = new FacebookAuthenticationToken(userDetails, grantedAuthorities);
        facebookAuthenticationToken.setAuthenticated(true);
        log.debug("Output parameter facebookAuthenticationToken=[{}]", facebookAuthenticationToken);
        return facebookAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.debug("input parameters authentication: [{}]", authentication);
        boolean isSupport = FacebookAuthenticationToken.class.isAssignableFrom(authentication);
        log.debug("Output parameter isSupport=[{}]", isSupport);
        return isSupport;
    }
}

