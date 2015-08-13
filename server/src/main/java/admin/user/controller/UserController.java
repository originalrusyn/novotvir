package admin.user.controller;

import admin.user.dto.CriteriaSuggestionsDTO;
import admin.user.service.UserSearchSuggestionServiceImpl;
import admin.user.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import web.persistence.domain.User;

import javax.annotation.Resource;
import java.util.Set;

import static java.util.Objects.nonNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

// @author: Titov Mykhaylo (titov) on 28.08.14 21:10.
@Controller
@RequestMapping("/admin")
@Slf4j
public class UserController {
    @Resource UserServiceImpl usersService;
    @Resource UserSearchSuggestionServiceImpl userSearchSuggestionService;

    @RequestMapping(value = {"/criteria"}, method = GET)
    public @ResponseBody CriteriaSuggestionsDTO getCriteriaSuggestionsDTO(){
        return userSearchSuggestionService.getCriteriaSuggestionsDTO();
    }

    @RequestMapping(value = {"/users"}, method = GET)
    public ModelAndView getUsersView(@RequestParam(required = false, value = "q") String criteria, @PageableDefault(page = 0, size = 50) Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("admin_users");
        if(nonNull(criteria)){
            Set<User> users = usersService.findUsers(criteria, pageable);
            modelAndView.addObject("users", users);
        }
        return modelAndView;
    }
}
