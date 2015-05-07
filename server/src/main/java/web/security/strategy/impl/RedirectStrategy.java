package web.security.strategy.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.util.UrlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @author: Titov Mykhaylo (titov) on 04.04.15 17:39.
@Slf4j
public class RedirectStrategy extends DefaultRedirectStrategy {

    private String calculateRedirectUrl(String contextPath, String url) {
        return UrlUtils.isAbsoluteUrl(url) ? url : contextPath + url;
    }


    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

        String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
        redirectUrl = response.encodeRedirectURL(redirectUrl);

        log.debug("Redirecting to {}" ,redirectUrl);

        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", redirectUrl);
    }
}
