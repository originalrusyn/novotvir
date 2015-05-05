package novotvir.controller;

import novotvir.dto.VersionDto;
import novotvir.service.VersionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

// @author Titov Mykhaylo (titov) on 04.04.2014.
@Controller
@RequestMapping("/web")
public class VersionController {

    @Resource VersionService versionService;

    @RequestMapping(value = "/version", method = GET)
    public ModelAndView getVersionModelAndView(){
        VersionDto versionDto = versionService.getVersion();

        ModelAndView versionModelAndView = new ModelAndView();
        versionModelAndView.addObject(versionDto);
        return versionModelAndView;
    }
}
