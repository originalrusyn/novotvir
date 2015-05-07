package web.security.social.facebook.provider.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import web.security.social.facebook.service.FacebookProfileRegService;
import web.security.social.facebook.token.impl.FacebookAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.facebook.api.FacebookProfile;

import java.util.Collection;

// @author Titov Mykhaylo (titov) on 11.01.14 20:09
@Slf4j
public class FacebookUserDetailsAuthProviderImpl implements AuthenticationProvider {

    @Setter GrantedAuthoritiesMapper authoritiesMapper;
    @Setter FacebookProfileRegService facebookProfileRegService;
    @Setter UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FacebookAuthenticationToken facebookAuthenticationToken = (FacebookAuthenticationToken) authentication;

        final FacebookProfile facebookProfile = facebookAuthenticationToken.getFacebookProfile();
        boolean signUp = facebookAuthenticationToken.isSignUp();

        UserDetails userDetails;
        if(signUp){
            userDetails = facebookProfileRegService.registerUser(facebookProfile);
        }else{
            userDetails = userDetailsService.loadUserByUsername(facebookProfile.getId());
        }

        Collection<? extends GrantedAuthority> grantedAuthorities = authoritiesMapper.mapAuthorities(userDetails.getAuthorities());

        facebookAuthenticationToken = new FacebookAuthenticationToken(userDetails, grantedAuthorities);
        facebookAuthenticationToken.setAuthenticated(true);
        return facebookAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return FacebookAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

