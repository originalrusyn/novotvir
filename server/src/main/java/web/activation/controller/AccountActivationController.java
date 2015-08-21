package web.activation.controller;

import common.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.account.dto.AccountDto;
import web.activation.exception.CouldNotActivateUserException;
import web.activation.service.UserActivationServiceImpl;
import web.persistence.domain.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static common.dto.ErrorDto.ERROR_DTO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SEE_OTHER;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static web.account.dto.AccountDto.accountDto;

// @author Titov Mykhaylo (titov) on 16.05.2014.
@Controller
@RequestMapping("/web")
@Slf4j
public class AccountActivationController {
    public static final String NAME_PATH_VAR = "name";
    public static final String ACTIVATION_TOKEN_REQ_PARAM = "activationToken";

    @Resource(name = "customRememberMeServices") RememberMeServices rememberMeServices;
    @Resource AuthenticationManager authenticationManagerWithoutPasswordEncoder;
    @Resource UserActivationServiceImpl userActivationService;

    @RequestMapping(value = "/users/{" + NAME_PATH_VAR + ":.*}", method = PUT)
    public ResponseEntity<AccountDto> activate(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @PathVariable(NAME_PATH_VAR) String userName,
                                 @RequestParam(ACTIVATION_TOKEN_REQ_PARAM) String activationToken){
        User user = userActivationService.activate(userName, activationToken);
        return autoLogin(request, response, user);
    }

    @ExceptionHandler({CouldNotActivateUserException.class})
    @ResponseStatus(BAD_REQUEST)
    public ModelAndView handleValidationException(CouldNotActivateUserException couldNotActivateUserException){
        log.warn(couldNotActivateUserException.getMessage());
        ModelAndView modelAndView = new ModelAndView("activation_can_not_be_completed");
        ErrorDto errorDto = new ErrorDto(couldNotActivateUserException.getErrCode(), couldNotActivateUserException.getLocalizedMessage());
        modelAndView.addObject(ERROR_DTO, errorDto);
        return modelAndView;
    }

    private ResponseEntity<AccountDto> autoLogin(HttpServletRequest request, HttpServletResponse response, User user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getName(), user.getToken());
        authentication.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticated = authenticationManagerWithoutPasswordEncoder.authenticate(authentication);
        rememberMeServices.loginSuccess(request, response, authenticated);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpHeaders headers = new HttpHeaders ();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath().path("users/" + user.getName()).build().toUri());
        return new ResponseEntity<>(accountDto(user), headers, SEE_OTHER);
    }
}
