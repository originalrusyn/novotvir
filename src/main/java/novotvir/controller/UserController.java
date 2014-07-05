package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Titov Mykhaylo (titov)
 *         20.02.14 20:16
 */
@Controller
@Slf4j
public class UserController {

    @RequestMapping(value = {"/", "/users/{name}"}, method = GET)
    public ModelAndView getAccountView(){
        return new ModelAndView("account");
    }
}
