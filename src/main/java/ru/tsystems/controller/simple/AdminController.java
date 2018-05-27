package ru.tsystems.controller.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.dto.OptionDTO;
import ru.tsystems.dto.TariffDTO;
import ru.tsystems.dto.UserDTO;
import ru.tsystems.service.ContractService;
import ru.tsystems.service.OptionService;
import ru.tsystems.service.TariffService;
import ru.tsystems.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private TariffService tariffService;
    @Autowired
    private ContractService contractService;

    @Autowired
    @Qualifier("adminCompoundValidator")
    private Validator compoundValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(compoundValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String adminHomePage(){
        return "admin/admin_home";
    }

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

    @RequestMapping(value = "options", method = RequestMethod.GET)
    public String optionControlPage(){
        return "admin/options";
    }

    @RequestMapping(value = "forbid_options", method = RequestMethod.GET)
    public String forbidOptionsGet(){
        return "admin/forbid_options";
    }




    @RequestMapping(value = "forbid_options", method = RequestMethod.POST)
    public String forbidOptionsPost(Model model,
                                    @RequestParam(name = "firstId") String id1,
                                    @RequestParam(name = "secondId") String id2){
        String resultMessage = "";

        if(id1.equals(id2)){
            resultMessage = "You can't forbid equal options";
        }else{
            optionService.addForbiddingOptions(id1, id2);
        }


        model.addAttribute("resultMessage", resultMessage);
        model.addAttribute("optionsList", optionService.getAllOptions());
        return "redirect:/admin/options_rules";
    }


    @RequestMapping(value = "required_options", method = RequestMethod.GET)
    public String requiredOptionsGet(Model model){
        model.addAttribute("optionsList", optionService.getAllOptions());
        return "admin/required_options";
    }

    @RequestMapping(value = "required_options", method = RequestMethod.POST)
    public String requiredOptionsPost(Model model,
                                    @RequestParam(name = "firstId") String id1,
                                    @RequestParam(name = "secondId") String id2){
        String resultMessage = "";
        if(id1.equals(id2)){
            resultMessage = "You can't make option require itself";
        }else{
            optionService.addRequiredOption(id1, id2);
        }
        model.addAttribute("resultMessage", resultMessage);
        model.addAttribute("optionsList", optionService.getAllOptions());
        return "redirect:/admin/options_rules";
    }


    @RequestMapping(value = "delete_tariff", method = RequestMethod.GET)
    public String deleteTariffGet(Model model){
        model.addAttribute("tariffList", tariffService.getAllTariffs());
        return "admin/delete_tariff";
    }

    @RequestMapping(value = "delete_tariff", method = RequestMethod.POST)
    public String deleteTariffPost(Model model,
                                   @RequestParam(name = "tariffId") String tariffId){
        tariffService.archive(Long.valueOf(tariffId));
        model.addAttribute("tariffList", tariffService.getAllTariffs());
        return "admin/delete_tariff";
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

    @RequestMapping(value = "find_contract", method = RequestMethod.GET)
    public String findContractPage(){
        return "clearmin/admin/find_contract";
    }

    @RequestMapping(value = "all_tariffs", method = RequestMethod.GET)
    public String allTariffsPage(){
        return "clearmin/admin/all_tariffs";
    }

    @RequestMapping(value = "all_options", method = RequestMethod.GET)
    public String allOptionsPage(){
        return "clearmin/admin/all_options";
    }

    @RequestMapping(value = "client_search", method = RequestMethod.GET)
    public String clientSearchGet(Model model){
        model.addAttribute("errorMessage", "");
        return "admin/client_search";
    }

    @RequestMapping(value = "client_search", method = RequestMethod.POST)
    public String clientSearchPost(Model model,
                                   @RequestParam(name = "number") String number){
        UserDTO user = userService.getClientByNumber(number);
        if(Objects.isNull(user)){
            model.addAttribute("errorMessage", "No results :(");
            return "admin/client_search";
        }else{
            model.addAttribute("user", user);
            model.addAttribute("contracts", user.getContracts());
            return "admin_display_user";
        }
    }


    @RequestMapping(value = "show_contract", method = RequestMethod.POST)
    public String showContractPost(Model model,
                                   @RequestParam(name = "contractId") String contractId){
        ContractDTO contractDTO = contractService.findContractById(Long.valueOf(contractId));
        model.addAttribute("contract", contractDTO);
        return "admin/show_contract";
    }


    @RequestMapping(value = "block_contract", method = RequestMethod.POST)
    public String blockContract(Model model,
                                @RequestParam(name = "contractId") String contractId){
        contractService.adminBlocksContract(Long.valueOf(contractId));
        return "redirect:/admin";
    }

    @RequestMapping(value = "unblock_contract", method = RequestMethod.POST)
    public String unblockContract(Model model,
                                @RequestParam(name = "contractId") String contractId){
        contractService.adminUnblocksContract(Long.valueOf(contractId));
        return "redirect:/admin";
    }

    @RequestMapping(value = "options_for_activation", method = RequestMethod.POST)
    public String optionsForActivation(Model model,
                                       @RequestParam(name = "contractId") String contractId){
        model.addAttribute("options", contractService.getOptionsForActivation(Long.valueOf(contractId)));
        model.addAttribute("contractId", contractId);
        return "admin/options_for_activation";
    }


    @RequestMapping(value = "activate_option", method = RequestMethod.POST)
    public String activateOption(Model model,
                                 @RequestParam(name = "contractId") String contractId,
                                 @RequestParam(name = "optionId") String optionId){
        contractService.enableOption(Long.valueOf(contractId), Long.valueOf(optionId));
        return "redirect:/admin";
    }

    @RequestMapping(value = "disable_option", method = RequestMethod.POST)
    public String disableOption(Model model,
                                @RequestParam(name = "contractId") String contractId,
                                @RequestParam(name = "optionId") String optionId){
        contractService.disableOption(Long.valueOf(contractId), Long.valueOf(optionId));
        return "redirect:/admin";
    }

    @RequestMapping(value = "tariffs_for_change", method = RequestMethod.POST)
    public String showTariffsForChange(Model model, @RequestParam(name = "contractId") String contractId){
        List<TariffDTO> tariffs = tariffService.getActiveTariffs();
        model.addAttribute("tariffs", tariffs);
        model.addAttribute("contractId", contractId);

        return "admin/tariffs_for_change";
    }

    @RequestMapping(value = "change_tariff", method = RequestMethod.POST)
    public String changeTariff(Model model, @RequestParam(name = "contractId") String contractId,
                               @RequestParam(name = "tariffId") String tariffId){
        contractService.changeTariff(Long.valueOf(contractId), Long.valueOf(tariffId));

        return "redirect:/admin";
    }


    @RequestMapping(value = "options_rules", method = RequestMethod.GET)
    public String optionRules(Model model){
        model.addAttribute("forbidRules", optionService.getForbiddingRules());
        model.addAttribute("requireRules", optionService.getRequirementRules());

        return "admin/options_rules";
    }


    @RequestMapping(value = "delete_forbid_rule", method = RequestMethod.POST)
    public String deleteForbidRule(Model model,
                                   @RequestParam(name = "firstOption") String firstOption,
                                   @RequestParam(name = "secondOption") String secondOption){
        optionService.deleteForbiddingRule(Long.valueOf(secondOption), Long.valueOf(firstOption));

        return "redirect:/admin/options_rules";
    }

    @RequestMapping(value = "delete_require_rule", method = RequestMethod.POST)
    public String deleteRequiredRule(Model model,
                                 @RequestParam(name = "firstOption") String firstOption,
                                 @RequestParam(name = "secondOption") String secondOption){
        optionService.deleteRequiringRule(Long.valueOf(firstOption), Long.valueOf(secondOption));

        return "redirect:/admin/options_rules";
    }

    @RequestMapping(value = "edit_tariff", method = RequestMethod.GET)
    public String editTariffGet(Model model){
        model.addAttribute("tariffDTO", new TariffDTO());
        model.addAttribute("options", optionService.getAllOptions());
        model.addAttribute("tariffs", tariffService.getActiveTariffs());

        return "admin/edit_tariff";
    }

    @RequestMapping(value = "edit_tariff", method = RequestMethod.POST)
    public String editTariffGet(Model model, @ModelAttribute(name = "tariffDTO") TariffDTO tariffDTO){
        List<Long> optionIds = new ArrayList<>();
        for(String sId : Arrays.asList(tariffDTO.getOptionIds())){
            optionIds.add(Long.valueOf(sId));
        }
        tariffService.changeOptions(tariffDTO.getId(), optionIds);

        return "redirect:/admin";
    }
}
