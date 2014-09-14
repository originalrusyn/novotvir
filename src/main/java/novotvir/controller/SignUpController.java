package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import novotvir.dto.AccountDto;
import novotvir.dto.UserRegDetailsDto;
import novotvir.persistence.domain.User;
import novotvir.service.UserEmailRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

import static novotvir.dto.UserRegDetailsDto.USER_REG_DETAILS_DTO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

//    @RequestMapping(value = "/signup", method = POST)
//    public ResponseEntity<AccountDto> signUp(@Valid @ModelAttribute(USER_REG_DETAILS_DTO) UserRegDetailsDto userRegDetailsDto) throws URISyntaxException {
//        User user = userEmailRegService.registerUser(userRegDetailsDto);
//        //ModelAndView modelAndView = new ModelAndView("redirect:signUpSuccessful");
//        //redirectAttributes.addFlashAttribute(ACCOUNT_DTO, new AccountDto(user));
//
//        HttpHeaders headers = new HttpHeaders ();
//        headers.setLocation(new URI("http://localhost:8080/signUpSuccessful"));
//        ResponseEntity<AccountDto> responseEntity = new ResponseEntity<>(AccountDto.accountDto(user), headers, HttpStatus.FOUND);
//        return responseEntity;
//    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signUp(@Valid @ModelAttribute(USER_REG_DETAILS_DTO) UserRegDetailsDto userRegDetailsDto) {
        User user = userEmailRegService.registerUser(userRegDetailsDto);
        ModelAndView modelAndView = new ModelAndView("redirect:reg_successful");
        modelAndView.addObject(AccountDto.ACCOUNT_DTO, AccountDto.accountDto(user));
        return modelAndView;
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


