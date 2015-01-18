package novotvir.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.CONFLICT;

// @author Titov Mykhaylo (titov) on 04.04.2014.
@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(value = CONFLICT)
    public ModelAndView handleConstraintViolationException(HttpServletRequest req, DataIntegrityViolationException dataIntegrityViolationException){
        log.trace("Data integrity violation", dataIntegrityViolationException);

        ModelAndView errorModelAndView = new ModelAndView("error");
        errorModelAndView.addObject("errorReason", "data.integrity.violation");
        errorModelAndView.addObject("exception", dataIntegrityViolationException);
        errorModelAndView.addObject("url", req.getRequestURL());
        return errorModelAndView;
    }
}
