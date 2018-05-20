package ru.tsystems.controller.simple;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.tsystems.service.OptionService;
import ru.tsystems.service.TariffService;

import java.security.Principal;
import java.util.Arrays;


@Controller
public class HomeController {
    @Autowired
    private TariffService tariffService;
    @Autowired
    private OptionService optionService;

    private static final Logger logger = Logger.getLogger(HomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal){

        return "index";
    }

    @RequestMapping(value = "tariffs", method = RequestMethod.GET)
    public String showTariffs(Model model){
        model.addAttribute("tariffs", tariffService.getAllTariffs());
        return "tariffs";
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String testingPage(){
        return "test";
    }

    @RequestMapping(value = "test2", method = RequestMethod.GET)
    public String testingPage2(){
        return "admin/test2";
    }

    @RequestMapping(value = "options", method = RequestMethod.GET)
    public @ResponseBody
    String showOptions(Model model){
        return Arrays.toString(optionService.getAllOptions().toArray());
    }
}
