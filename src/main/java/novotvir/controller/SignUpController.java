package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import novotvir.dto.AccountDto;
import novotvir.dto.UserRegDetailsDto;
import novotvir.persistence.domain.User;
import novotvir.service.UserEmailRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URISyntaxException;

import static novotvir.dto.AccountDto.accountDto;
import static novotvir.dto.UserRegDetailsDto.USER_REG_DETAILS_DTO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SEE_OTHER;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author: Titov Mykhaylo (titov)
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
    public ResponseEntity<AccountDto> signUp(@Valid @ModelAttribute(USER_REG_DETAILS_DTO) UserRegDetailsDto userRegDetailsDto) throws URISyntaxException {
        User user = userEmailRegService.registerUser(userRegDetailsDto);

        HttpHeaders headers = new HttpHeaders ();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath().path("signUpSuccessful").build().toUri());
        return new ResponseEntity<>(accountDto(user), headers, SEE_OTHER);
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(BAD_REQUEST)
    public ModelAndView handleValidationException(BindException bindException){
        log.trace("Bad request", bindException);
        ModelAndView signUpModelAndView = new ModelAndView("signup");
        signUpModelAndView.addAllObjects(bindException.getModel());
        return signUpModelAndView;
    }
}


