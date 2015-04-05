package novotvir.controller;

import novotvir.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import static novotvir.dto.AccountDto.ACCOUNT_DTO;
import static novotvir.dto.AccountDto.accountDto;
import static novotvir.utils.UserContext.getSecurityContextDetails;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

// @author: Mykhaylo Titov on 18.01.15 21:03.
@Controller
@RequestMapping("/web")
public class AccountController {

    @Resource UserService userService;

    @RequestMapping(value = {"/", "/users/{name}"}, method = GET)
    public ModelAndView account(){
        return new ModelAndView("account", ACCOUNT_DTO, accountDto(userService.getUser(getSecurityContextDetails().getUserId())));
    }
}
