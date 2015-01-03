package novotvir.controller;

import novotvir.dto.VersionDto;
import novotvir.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

// @author Titov Mykhaylo (titov) on 04.04.2014.
@Controller
public class VersionController {

    @Autowired
    VersionService versionService;

    @RequestMapping(value = "/version", method = GET)
    public ModelAndView getVersionModelAndView(){
        VersionDto versionDto = versionService.getVersion();

        ModelAndView versionModelAndView = new ModelAndView();
        versionModelAndView.addObject(versionDto);
        return versionModelAndView;
    }
}
