package ru.tsystems.controller.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.service.ContractService;
import ru.tsystems.service.OptionService;
import ru.tsystems.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private ContractService contractService;

    @RequestMapping(value = "new_client", method = RequestMethod.GET)
    public String getRegistrationPage() {
        return "clearmin/admin/new_client";
    }

    @RequestMapping(value = "new_tariff", method = RequestMethod.GET)
    public String getNewTariffPage() {
        return "clearmin/admin/new_tariff";
    }

    @RequestMapping(value = "new_contract", method = RequestMethod.GET)
    public String newContractPage() {
        return "clearmin/admin/new_contract";
    }

    @RequestMapping(value = "new_option", method = RequestMethod.GET)
    public String addNewOption(){
        return "clearmin/admin/new_option";
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String testPage(){
        return "clearmin/admin/test";
    }

    @RequestMapping(value = "all_clients", method = RequestMethod.GET)
    public String allClientsPage(){
        return "clearmin/admin/all_clients";
    }

    @RequestMapping(value = "find_client", method = RequestMethod.GET)
    public String findClientPage(){
        return "clearmin/admin/find_client";
    }

    @RequestMapping(value = "all_contracts", method = RequestMethod.GET)
    public String allContractsPage(){
        return "clearmin/admin/all_contracts";
    }

    @RequestMapping(value = "all_tariffs", method = RequestMethod.GET)
    public String allTariffsPage(){
        return "clearmin/admin/all_tariffs";
    }

    @RequestMapping(value = "all_options", method = RequestMethod.GET)
    public String allOptionsPage(){
        return "clearmin/admin/all_options";
    }

    @RequestMapping(value = "tariff", method = RequestMethod.GET)
    public String tariffPage(@RequestParam Long id, Model model){
       model.addAttribute("tariffId", id);
       return "clearmin/admin/tariff";
    }

    @RequestMapping(value = "show_contract", method = RequestMethod.GET)
    public String contractPage(@RequestParam String number, Model model){
        ContractDTO contractDTO = contractService.findContractByNumber(number);
        model.addAttribute("user", userService.findUserByNumber(contractDTO));
        model.addAttribute("contract", contractDTO);
        return "clearmin/admin/show_contract";
    }

    @RequestMapping(value = "all_rules", method = RequestMethod.GET)
    public String allRulesPage(Model model){
        model.addAttribute("forbiddingRules", optionService.getForbiddingRules());
        model.addAttribute("requiringRules", optionService.getRequiringRules());
        return "clearmin/admin/all_rules";
    }

    @RequestMapping(value = "new_rule", method = RequestMethod.GET)
    public String newRulePage(){
        return "clearmin/admin/new_rule";
    }

}
