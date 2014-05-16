package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import novotvir.persistence.domain.User;
import novotvir.service.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author Titov Mykhaylo (titov) on 16.05.2014.
 */
@Controller
@Slf4j
public class AccountActivationController {
    public static final String ACTIVATION_TOKEN_PATH_VAR = "activationToken";

    @Autowired RememberMeServices rememberMeServices;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired UserActivationService userActivationService;

    @RequestMapping(value = "/accounts/{"+ ACTIVATION_TOKEN_PATH_VAR +":.*}", method = PUT)
    public ModelAndView activate(HttpServletRequest request, HttpServletResponse response, @PathVariable(ACTIVATION_TOKEN_PATH_VAR) String activationToken){
        User user = userActivationService.activate();
        return autoLogin(request, response, user);
    }

    private ModelAndView autoLogin(HttpServletRequest request, HttpServletResponse response, User user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.name, user.token);
        authentication.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticated = authenticationManager.authenticate(authentication);
        rememberMeServices.loginSuccess(request, response, authenticated);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ModelAndView("redirect:account");
    }
}
