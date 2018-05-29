package ru.tsystems.controller.simple;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.UserDTO;
import ru.tsystems.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping(value = "/client")
public class ClientController {
    @Autowired
    private UserService userService;

    @RequestMapping
    public String getHomeClientPage(Model model, Principal principal){
        UserDTO user = userService.findUserByEmail(principal.getName());

        model.addAttribute("client", user);
        return "clearmin/client/client_home";
    }

    @RequestMapping(value = "tariffs", method = RequestMethod.GET)
    public String showContract(Model model, Principal principal){
        model.addAttribute("user", userService.findUserByEmail(principal.getName()));
        return "clearmin/client/tariffs";
    }

    @RequestMapping(value = "options", method = RequestMethod.GET)
    public String optionsPage(Model model, Principal principal) {
        model.addAttribute("user", userService.findUserByEmail(principal.getName()));
        return "clearmin/client/options";
    }

}
