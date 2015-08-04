package admin.security.handler.impl;

import admin.persistence.domain.Admin;
import admin.persistence.repository.AdminRepository;
import admin.security.credential.impl.AdminDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static common.util.RequestUtils.getRemoteAddr;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

// @author: Mykhaylo Titov on 16.05.15 20:07.
@Component
public class AdminRememberMeSuccessfulHandler implements AuthenticationSuccessHandler {

    @Resource AdminRepository adminRepository;

    @Override
    @Transactional(propagation = REQUIRED)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        String requestURI = request.getRequestURI();
        if(principal instanceof AdminDetailsImpl && requestURI.endsWith("/signin")) {
            long userId = ((AdminDetailsImpl) principal).getId();
            Admin admin = adminRepository.findOne(userId);
            adminRepository.save(admin.setLastSignInIpAddress(getRemoteAddr()).setLastSignInDateTime(LocalDateTime.now()));
        }
    }
}
