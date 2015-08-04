package admin.security.credential.impl;

import admin.persistence.domain.Admin;
import admin.persistence.domain.AdminAuthority;
import admin.security.credential.AdminSecurityContextDetails;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// @author Titov Mykhaylo (titov) on 06.07.14.
public class AdminDetailsImpl implements UserDetails, AdminSecurityContextDetails {

    private static final long serialVersionUID = 3753615312297453066L;
    private Admin admin;
    private List<GrantedAuthority> grantedAuthorities;

    public AdminDetailsImpl(@NonNull Admin admin) {
        this.admin = admin;

        grantedAuthorities = new ArrayList<>();

        for (AdminAuthority authority : admin.getAuthorities()) {
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
        return admin.getToken();
    }

    @Override
    public String getUsername() {
        return admin.getEmail();
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
        return admin.getId();
    }
}
