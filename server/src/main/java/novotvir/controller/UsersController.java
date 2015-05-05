package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import novotvir.dto.CriteriaSuggestionsDTO;
import novotvir.persistence.domain.User;
import novotvir.service.UserSearchSuggestionService;
import novotvir.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Set;

import static java.util.Objects.nonNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

// @author: Titov Mykhaylo (titov) on 28.08.14 21:10.
@Controller
@RequestMapping("/admin")
@Slf4j
public class UsersController {
    @Resource UsersService usersService;
    @Resource UserSearchSuggestionService userSearchSuggestionService;

    @RequestMapping(value = {"/criteria"}, method = GET)
    public @ResponseBody CriteriaSuggestionsDTO get(){
        return userSearchSuggestionService.getCriteriaSuggestionsDTO();
    }

    @RequestMapping(value = {"/users"}, method = GET)
    public ModelAndView getUsersView(@RequestParam(required = false, value = "q") String criteria){
        ModelAndView modelAndView = new ModelAndView("admin_users");
        if(nonNull(criteria)){
            Set<User> users = usersService.findUsers(criteria);
            modelAndView.addObject("users", users);
        }
        return modelAndView;
    }
}
