package novotvir.controller;

import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.security.credential.impl.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import static novotvir.dto.AccountDto.ACCOUNT_DTO;
import static novotvir.dto.AccountDto.accountDto;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

// @author: Mykhaylo Titov on 13.09.14 22:01.
@Controller
public class SignUpCongratulationController {

    @Resource UserRepository userRepository;

    @RequestMapping(value = "/signUpSuccessful", method = GET)
    public ModelAndView getSignUpSuccessfulModelAndView(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findOne(userDetails.getUserId());

        ModelAndView modelAndView = new ModelAndView("sign_up_successful");
        modelAndView.addObject(ACCOUNT_DTO, accountDto(user));
        return modelAndView;
    }
}
