package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import novotvir.dto.CriteriaSuggestionsDTO;
import novotvir.persistence.domain.User;
import novotvir.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

import static java.util.Objects.nonNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static scala.actors.threadpool.Arrays.asList;

// @author: Titov Mykhaylo (titov) on 28.08.14 21:10.
@Controller
@Slf4j
public class UsersController {
    @Autowired UsersService usersService;

//    @RequestMapping(value = {"/admin/criteria"}, method = GET)
//    public ModelAndView get(){
//        ModelAndView modelAndView = new ModelAndView("admin_users");
//        modelAndView.addObject(new CriteriaSuggestionsDTO().setSuggestions(asList(new String[]{"name"})));
//        return modelAndView;
//    }

    @RequestMapping(value = {"/admin/criteria"}, method = GET)
    public @ResponseBody CriteriaSuggestionsDTO get(){
        return new CriteriaSuggestionsDTO().setSuggestions(asList(new String[]{"name"}));
    }

    @RequestMapping(value = {"/admin/users"}, method = GET)
    public ModelAndView getUsersView(@RequestParam(required = false, value = "q") String criteria){
        ModelAndView modelAndView = new ModelAndView("admin_users");
        if(nonNull(criteria)){
            Set<User> users = usersService.findUsers(criteria);
            modelAndView.addObject("users", users);
        }
        return modelAndView;
    }
}
