package web.security.social.facebook.token.impl;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.FacebookProfile;

import java.util.Collection;

// @author Titov Mykhaylo (titov) on 11.01.14 20:12
@Getter
@ToString
public class FacebookAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 4205964421055985979L;

    private Object credentials;
    private FacebookProfile facebookProfile;
    private UserDetails principal;
    private boolean signUp;

    public FacebookAuthenticationToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = userDetails;
    }

    public FacebookAuthenticationToken(FacebookProfile facebookProfile, boolean signUp) {
        super(null);
        this.facebookProfile = facebookProfile;
        this.signUp = signUp;
    }
}