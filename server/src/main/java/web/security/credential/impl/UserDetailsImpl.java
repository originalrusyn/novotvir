package web.security.credential.impl;

import lombok.Getter;
import lombok.ToString;
import web.persistence.domain.Authority;
import web.persistence.domain.User;
import common.security.credential.SecurityContextDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// @author Titov Mykhaylo (titov) on 11.01.14 20:25
@ToString
public class UserDetailsImpl implements UserDetails, SecurityContextDetails {

    private static final long serialVersionUID = 3753615312297453066L;
    private @Getter User user;
    private List<GrantedAuthority> grantedAuthorities;

    public UserDetailsImpl(User user) {
        this.user = user;

        grantedAuthorities = new ArrayList<>();

        for (Authority authority : user.authorities) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.role.name());
            grantedAuthorities.add(grantedAuthority);
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.token;
    }

    @Override
    public String getUsername() {
        return user.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public long getUserId() {
        return user.id;
    }
}