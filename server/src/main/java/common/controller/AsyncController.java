package common.controller;

import lombok.extern.slf4j.Slf4j;
import common.service.CustomMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

// @author Titov Mykhaylo (titov) on 15.06.13 10:13
@Controller
@Slf4j
@RequestMapping("/web")
public class AsyncController {

    @Resource CustomMessageSource customMessageSource;

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
