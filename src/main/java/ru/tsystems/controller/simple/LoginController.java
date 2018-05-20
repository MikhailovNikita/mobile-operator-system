package ru.tsystems.controller.simple;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPageGet(@RequestParam(name = "error", required = false) boolean loginError,
                               Model model) {
        if (loginError) logger.info("Invalid username/password");
        model.addAttribute("errorMessage", loginError ? "Wrong email or password" : "");
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPagePost(@RequestParam(name = "error", required = false) boolean loginError,
                                Model model) {

        if (loginError) logger.info("Invalid username/password");
        model.addAttribute("errorMessage", loginError ? "Wrong email or password" : "");
        return "login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logoutPageGet() {
        return "redirect:/";
    }

    @RequestMapping(value = "password_reset", method = RequestMethod.GET)
    public String resetPasswordGet(){
        throw new UnsupportedOperationException();
    }
}
