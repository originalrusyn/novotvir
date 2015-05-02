package novotvir.security.social.google.token.impl;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.google.api.plus.Person;

import java.util.Collection;

// @author: m on 01.05.15 16:06.
@Getter
@ToString
public class GoogleAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 4205964421055985979L;

    private Object credentials;
    private Person person;
    private UserDetails principal;
    private boolean signUp;

    public GoogleAuthenticationToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = userDetails;
    }

    public GoogleAuthenticationToken(Person person, boolean signUp) {
        super(null);
        this.person = person;
        this.signUp = signUp;
    }
}
