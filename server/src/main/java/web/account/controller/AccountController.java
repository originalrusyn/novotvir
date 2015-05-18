package web.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import web.account.service.UserServiceImpl;

import javax.annotation.Resource;

import static web.account.dto.AccountDto.ACCOUNT_DTO;
import static web.account.dto.AccountDto.accountDto;
import static common.util.UserContext.getSecurityContextDetails;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

// @author: Mykhaylo Titov on 18.01.15 21:03.
@Controller
@RequestMapping("/web")
public class AccountController {

    @Resource UserServiceImpl userService;

    @RequestMapping(value = {"/", "/users/{name}"}, method = GET)
    public ModelAndView account(){
        return new ModelAndView("account", ACCOUNT_DTO, accountDto(userService.getUser(getSecurityContextDetails().getId())));
    }
}
