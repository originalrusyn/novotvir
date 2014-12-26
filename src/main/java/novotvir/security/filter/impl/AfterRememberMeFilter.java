package novotvir.security.filter.impl;

import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

// @author Titov Mykhaylo (titov) on 11.01.14 22:49
public class AfterRememberMeFilter extends GenericFilterBean {

    private SimpleGrantedAuthority roleUserSimpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
    @Setter
    private TokenBasedRememberMeServices tokenBasedRememberMeServices;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(authentication) && authentication.isAuthenticated()) {
            if (authentication.getAuthorities().contains(roleUserSimpleGrantedAuthority))
                tokenBasedRememberMeServices.loginSuccess(httpServletRequest, httpServletResponse, authentication);
        }
        chain.doFilter(request, response);
    }
}