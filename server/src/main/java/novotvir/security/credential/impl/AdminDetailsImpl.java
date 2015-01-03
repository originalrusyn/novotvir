package novotvir.security.credential.impl;

import novotvir.persistence.domain.Admin;
import novotvir.persistence.domain.AdminAuthority;
import novotvir.security.credential.SecurityContextDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// @author Titov Mykhaylo (titov) on 06.07.14.
public class AdminDetailsImpl implements UserDetails, SecurityContextDetails {

    private static final long serialVersionUID = 3753615312297453066L;
    private Admin admin;
    private List<GrantedAuthority> grantedAuthorities;

    public AdminDetailsImpl(Admin admin) {
        this.admin = admin;

        grantedAuthorities = new ArrayList<>();

        for (AdminAuthority authority : admin.authorities) {
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
        return admin.token;
    }

    @Override
    public String getUsername() {
        return admin.email;
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
        return admin.id;
    }

    @Override
    public List<GrantedAuthority> getUserAuthorities() {
        return grantedAuthorities;
    }
}
