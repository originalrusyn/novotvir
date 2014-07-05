package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import novotvir.dto.UserRegDetailsDto;
import novotvir.persistence.domain.User;
import novotvir.service.UserEmailRegService;
import novotvir.service.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static novotvir.dto.UserRegDetailsDto.USER_REG_DETAILS_DTO;
import static novotvir.persistence.domain.User.USER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @autor: Titov Mykhaylo (titov)
 * 18.02.14 15:53
 */
@Controller
@Slf4j
public class SignUpController {

    @Autowired UserEmailRegService userEmailRegService;
    @Resource(name = "customRememberMeServices") RememberMeServices rememberMeServices;

    @RequestMapping(value = "/signup", method = GET)
    public ModelAndView getSignUpModelAndView() {
        ModelAndView signUpModelAndView = new ModelAndView("signup");
        signUpModelAndView.addObject(USER_REG_DETAILS_DTO, new UserRegDetailsDto());
        return signUpModelAndView;
    }

    @RequestMapping(value = "/signup", method = POST)
    @ResponseStatus(CREATED)
    public ModelAndView signUp(HttpServletRequest request,
                               @Valid @ModelAttribute(USER_REG_DETAILS_DTO) UserRegDetailsDto userRegDetailsDto) {
        log.debug("input parameters request, userRegDetailsDto: [{}], [{}]", new Object[]{request, userRegDetailsDto});
        User user = userEmailRegService.registerUser(userRegDetailsDto);
        ModelAndView modelAndView = new ModelAndView("redirect:reg_successful");
        modelAndView.addObject(USER, user);
        return modelAndView;
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(BAD_REQUEST)
    public ModelAndView handleValidationException(BindException bindException){
        log.trace(bindException.getMessage(), bindException);
        ModelAndView signUpModelAndView = new ModelAndView("signup");
        signUpModelAndView.addAllObjects(bindException.getModel());
        return signUpModelAndView;
    }
}


