package ru.tsystems.controller.simple;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPageGet(@RequestParam(name = "error", required = false) boolean loginError,
                               Model model) {
        model.addAttribute("errorMessage", loginError ? "Wrong email or password" : "");
        return "client/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPagePost(@RequestParam(name = "error", required = false) boolean loginError,
                                Model model) {

        model.addAttribute("errorMessage", loginError ? "Wrong email or password" : "");
        return "client/login";
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
