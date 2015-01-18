package novotvir.utils;

import novotvir.security.credential.impl.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

// @author: Mykhaylo Titov on 18.01.15 21:52.
public class UserContext {


    public static UserDetailsImpl getSecurityContextDetails() {
        return (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
