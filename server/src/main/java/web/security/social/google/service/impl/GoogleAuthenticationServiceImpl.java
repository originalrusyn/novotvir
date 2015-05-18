package web.security.social.google.service.impl;

import org.springframework.social.google.security.GoogleAuthenticationService;

import javax.servlet.http.HttpServletRequest;

// @author: Mykhaylo Titov on 10.05.15 10:41.
public class GoogleAuthenticationServiceImpl extends GoogleAuthenticationService {
    public GoogleAuthenticationServiceImpl(String apiKey, String appSecret) {
        super(apiKey, appSecret);
    }

    @Override
    protected String buildReturnToUrl(HttpServletRequest request) {
        boolean installedApp = Boolean.parseBoolean(request.getParameter("installedApp"));
        return installedApp? "" : super.buildReturnToUrl(request);
    }
}
