package novotvir.controller;

import lombok.extern.slf4j.Slf4j;
import novotvir.service.CustomMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Titov Mykhaylo (titov)
 * 15.06.13 10:13
 */
@Controller
@Slf4j
public class AsyncController {

    @Autowired
    CustomMessageSource messageSource;

    @RequestMapping(value = "/do", method = GET)
    @ResponseBody
    Callable<String> hi() {
        log.debug("gg");
        return ()-> "g";
    }

    @RequestMapping(value = "/m", method = GET)
    @ResponseBody
    Callable<String> m() {
        log.debug("m");
        throw new RuntimeException("");
    }

}
