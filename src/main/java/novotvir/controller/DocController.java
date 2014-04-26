package novotvir.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @autor: Titov Mykhaylo (titov)
 * 21.02.14 18:04
 */
@Controller
public class DocController {

    @RequestMapping(value = "/doc", method =  GET)
    public ModelAndView getDocView(){
        return new ModelAndView("doc");
    }
}
