package common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

// @author Titov Mykhaylo (titov) on 02.04.2014.
@Controller
public class ErrorController {

     @RequestMapping(value = "/page_not_found", method = GET)
     @ResponseStatus(NOT_FOUND)
     public ModelAndView getResourceNotFoundView(){
        return new ModelAndView("page_not_found");
    }

    @RequestMapping(value = "/internal_server_error", method = GET)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ModelAndView getResourceInternalServerErrorView(){
        return new ModelAndView("internal_server_error");
    }

    @RequestMapping(value = "/access_is_denied", method = GET)
    @ResponseStatus(FORBIDDEN)
    public ModelAndView getAccessDeniedView(){
        return new ModelAndView("access_is_denied");
    }
}
