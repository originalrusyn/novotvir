package novotvir.security.social.google.provider.impl;

import lombok.Setter;
import novotvir.security.social.google.service.GoogleProfileRegService;
import novotvir.security.social.google.token.impl.GoogleAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.google.api.plus.Person;

import java.util.Collection;

// @author: m on 01.05.15 16:59.
public class GoogleUserDetailsAuthProviderImpl implements AuthenticationProvider {

    @Setter GrantedAuthoritiesMapper authoritiesMapper;
    @Setter GoogleProfileRegService googleProfileRegService;
    @Setter UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        GoogleAuthenticationToken googleAuthenticationToken = (GoogleAuthenticationToken) authentication;

        final Person person = googleAuthenticationToken.getPerson();
        boolean signUp = googleAuthenticationToken.isSignUp();

        UserDetails userDetails;
        if(signUp){
            userDetails = googleProfileRegService.registerUser(person);
        }else{
            userDetails = userDetailsService.loadUserByUsername(person.getId());
        }

        Collection<? extends GrantedAuthority> grantedAuthorities = authoritiesMapper.mapAuthorities(userDetails.getAuthorities());

        googleAuthenticationToken = new GoogleAuthenticationToken(userDetails, grantedAuthorities);
        googleAuthenticationToken.setAuthenticated(true);
        return googleAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return GoogleAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
