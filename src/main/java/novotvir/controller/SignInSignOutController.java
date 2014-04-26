package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @autor: Titov Mykhaylo (titov)
 * 18.02.14 15:16
 */
@Controller
@Slf4j
public class SignInSignOutController {

    @RequestMapping(value = {"/signin", "/signout"}, method = GET)
    public ModelAndView signInSignOut(HttpServletRequest request) {
        log.info(request.getHeader("Accept"));
        return new ModelAndView("signin");
    }
}
