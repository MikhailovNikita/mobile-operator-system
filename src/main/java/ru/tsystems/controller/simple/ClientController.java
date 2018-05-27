package ru.tsystems.controller.simple;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.dto.TariffDTO;
import ru.tsystems.dto.UserDTO;
import ru.tsystems.service.ContractService;
import ru.tsystems.service.TariffService;
import ru.tsystems.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/client")
public class ClientController {
    @Autowired
    private UserService userService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private TariffService tariffService;


    @RequestMapping
    public String getHomeClientPage(Model model, Principal principal){
        UserDTO user = userService.findUserByEmail(principal.getName());

        model.addAttribute("name", user.getName() + " " + user.getLastName());
        return "client/client_home";
    }


    @RequestMapping(value = "contracts", method = RequestMethod.GET)
    public String getContractsPage(Model model, Principal principal){
        List<ContractDTO> contracts = userService.getAllClientsContracts(principal.getName());
        model.addAttribute("contractsList", contracts);
        return "client/client_contracts";
    }


    @RequestMapping(value = "contracts", method = RequestMethod.POST)
    public String showContract(Model model, Principal principal,
                               @ModelAttribute("contractDTO") ContractDTO contractDTO){
        model.addAttribute("tariffs", tariffService.getAllTariffs());
        model.addAttribute("contractDTO", contractService.findContractById(Long.valueOf(contractDTO.getContractId())));
        return "client/client_contracts";
    }

    @RequestMapping(value = "block_contract", method = RequestMethod.POST)
    public String blockContract(Model model,
                                @RequestParam(name = "contractId") String contractId){

        contractService.userBlocksContract(Long.valueOf(contractId));
        return "redirect:/client/contracts";
    }

    @RequestMapping(value = "unblock_contract", method = RequestMethod.POST)
    public String unblockContract(Model model,
                                  @RequestParam(name = "contractId") String contractId){

        contractService.userUnblocksContract(Long.valueOf(contractId));
        return "redirect:/client/contracts";
    }

    @RequestMapping(value = "show_contract", method = RequestMethod.POST)
    public String showContract(Model model, @RequestParam(name = "contractId") String contractId){
        ContractDTO contractDTO = contractService.findContractById(Long.valueOf(contractId));
        model.addAttribute("contract", contractDTO);
        return "client/show_contract";
    }

    @RequestMapping(value = "tariffs_for_change", method = RequestMethod.POST)
    public String showTariffsForChange(Model model, @RequestParam(name = "contractId") String contractId){
        List<TariffDTO> tariffs = tariffService.getActiveTariffs();
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("contractId", contractId);

        return "tariffs_for_change";
    }

    @RequestMapping(value = "change_tariff", method = RequestMethod.POST)
    public String changeTariff(Model model, @RequestParam(name = "contractId") String contractId,
                               @RequestParam(name = "tariffId") String tariffId){
        contractService.changeTariff(Long.valueOf(contractId), Long.valueOf(tariffId));

        return "redirect:/client/contracts";
    }

    @RequestMapping(value = "disable_option", method = RequestMethod.POST)
    public String disableOption(Model model,
                                @RequestParam(name = "contractId") String contractId,
                                @RequestParam(name = "optionId") String optionId){
        contractService.disableOption(Long.valueOf(contractId), Long.valueOf(optionId));

        return "redirect:/client/contracts";
    }

    @RequestMapping(value = "options_for_activation", method = RequestMethod.POST)
    public String showOptionsForActivation(Model model,
                                           @RequestParam(name = "contractId") String contractId){
        model.addAttribute("options", contractService.getOptionsForActivation(Long.valueOf(contractId)));
        model.addAttribute("contractId", contractId);
        return "options_for_activation";
    }

    @RequestMapping(value = "activate_option", method = RequestMethod.POST)
    public String activateOption(Model model,
                                 @RequestParam(name = "contractId") String contractId,
                                 @RequestParam(name = "optionId") String optionId){
        contractService.enableOption(Long.valueOf(contractId), Long.valueOf(optionId));
        return "redirect:/client/contracts";
    }

}
