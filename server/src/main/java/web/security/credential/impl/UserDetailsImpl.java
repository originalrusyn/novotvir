package web.security.credential.impl;

import common.security.credential.SecurityContextDetails;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import web.persistence.domain.Authority;
import web.persistence.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// @author Titov Mykhaylo (titov) on 11.01.14 20:25
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@ToString(exclude = "user")
public class UserDetailsImpl implements UserDetails, SecurityContextDetails {

    private static final long serialVersionUID = 3753615312297453066L;
    private @Getter User user;
    private List<GrantedAuthority> grantedAuthorities;

    public UserDetailsImpl(@NonNull User user) {
        this.user = user;

        grantedAuthorities = new ArrayList<>();

        for (Authority authority : user.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getRole().name());
            grantedAuthorities.add(grantedAuthority);
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getToken();
    }

    @Override
    public String getUsername() {
        return user.getName();
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
    public long getId() {
        return user.getId();
    }
}