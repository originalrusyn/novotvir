package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import novotvir.persistence.domain.User;
import novotvir.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

import static java.util.Objects.nonNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Titov Mykhaylo (titov)
 *         20.02.14 20:16
 */
@Controller
@Slf4j
public class UserController {

    @Autowired UsersService usersService;

    @RequestMapping(value = {"/", "/users/{name}"}, method = GET)
    public ModelAndView getAccountView(){
        return new ModelAndView("account");
    }

    @RequestMapping(value = {"/admin/users"}, method = GET)
    public ModelAndView getUsersView(@RequestParam(required = false) String q){
        ModelAndView modelAndView = new ModelAndView("admin_users");
        if(nonNull(q)){
            Set<User> users = usersService.findUsers(q);
            modelAndView.addObject("users", users);
        }
        return modelAndView;
    }
}
