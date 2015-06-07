package web.security.social.credential.impl;

import common.security.credential.SecurityContextDetails;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;
import web.persistence.domain.User;

import java.util.List;

// @author: Mykhaylo Titov on 18.05.15 22:11.
@ToString(exclude = "user")
@SuppressFBWarnings("EQ_DOESNT_OVERRIDE_EQUALS")
public class SocialUserImpl extends SocialUser implements SecurityContextDetails {

    @Getter final User user;

    public SocialUserImpl(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, List<GrantedAuthority> grantedAuthorities) {
        super(user.name, user.token, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
        this.user = user;
    }

    @Override
    public long getId() {
        return user.id;
    }

    @Override
    public String getUsername() {
        return user.name;
    }
}
