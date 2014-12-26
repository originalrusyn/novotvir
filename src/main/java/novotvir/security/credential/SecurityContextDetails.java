package novotvir.security.credential;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

// @author Titov Mykhaylo (titov) on 11.01.14 20:32
public interface SecurityContextDetails {
    public long getUserId();

    public String getUsername();

    public List<GrantedAuthority> getUserAuthorities();
}
