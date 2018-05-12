package ru.tsystems.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.dto.OptionDTO;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.dao.api.TariffDAO;
import ru.tsystems.persistence.dao.api.UserDAO;
import ru.tsystems.persistence.entity.Contract;
import ru.tsystems.persistence.entity.Tariff;
import ru.tsystems.persistence.entity.TariffOption;
import ru.tsystems.persistence.entity.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContractService {
    @Autowired
    private ContractDAO contractDAO;
    @Autowired
    private TariffDAO tariffDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private OptionDAO optionDAO;

    private static final Logger logger = Logger.getLogger(ContractService.class);

    public boolean isContractWithSuchNumberExist(String number) {
        return (contractDAO.getContractByNumber(number) != null);
    }

    public void addNewContract(ContractDTO contractDTO) {
        Tariff tariff = tariffDAO.get(contractDTO.getTariffDTO().getId());
        User user = userDAO.findUserByPassport(contractDTO.getOwnersPassport());
        Contract contract = new Contract();
        contract.setTariff(tariff);
        contract.setUser(user);
        contract.setNumber(contractDTO.getNumber());
        contract.setConclusionDate(new Date());
        contractDAO.persist(contract);
    }

    public ContractDTO findContractById(Long id) {
        return ContractDTO.toDTO(contractDAO.get(id));
    }

    public void userBlocksContract(Long id) {
        Contract contract = contractDAO.get(id);
        contract.setBlockedByUser(true);
        contractDAO.update(contract);
    }

    public void userUnblocksContract(Long id) {
        Contract contract = contractDAO.get(id);
        contract.setBlockedByUser(false);
        contractDAO.update(contract);
    }

    public void adminBlocksContract(Long id) {
        Contract contract = contractDAO.get(id);
        contract.setBlockedByAdmin(true);
        contractDAO.update(contract);
    }

    public void adminUnblocksContract(Long id) {
        Contract contract = contractDAO.get(id);
        contract.setBlockedByAdmin(false);
        contractDAO.update(contract);
    }

    public void changeTariff(Long contractId, Long tariffId) {
        Contract contract = contractDAO.get(contractId);
        if(contract.isBlockedByAdmin() || contract.isBlockedByUser()) return;

        Tariff tariff = tariffDAO.get(tariffId);
        contract.setTariff(tariff);

        contract.setEnabledOptions(new HashSet<>(
                contract.getEnabledOptions().stream()
                        .filter(x -> tariff.getOption().contains(x))
                        .collect(Collectors.toList())));
    }

    public void disableOption(Long contractId, Long optionId) {
        Contract contract = contractDAO.get(contractId);
        TariffOption option = optionDAO.get(optionId);
        contract.getEnabledOptions().remove(option);
        contract.setEnabledOptions(new HashSet<>(
                contract.getEnabledOptions().stream()
                .filter(x -> !x.getRequiredOptions().contains(option))
                .collect(Collectors.toList())));
    }

    public List<OptionDTO> getOptionsForActivation(Long contractId) {
        Contract contract = contractDAO.get(contractId);
        Set<TariffOption> enabledOptions = contract.getEnabledOptions();
        logger.debug(enabledOptions.size() + " options enabled for contract " + contract.getNumber());

        List<OptionDTO> options = contract.getTariff().getOption().stream()
                .filter(x -> !enabledOptions.contains(x))
                .filter(x -> contract.getTariff().getOption().contains(x))
                .filter(x -> contract.getEnabledOptions().containsAll(x.getRequiredOptions()))
                .filter(x -> Collections.disjoint(x.getForbiddingOptions(), contract.getEnabledOptions()))
                .map(OptionDTO::toDTO)
                .collect(Collectors.toList());

        logger.debug(options.size() + " options are available for activation");
        return options;
    }

    public void enableOption(Long contractId, Long optionId) {
        Contract contract = contractDAO.get(contractId);
        TariffOption option = optionDAO.get(optionId);
        if(contract.getEnabledOptions().containsAll(option.getRequiredOptions())
                && Collections.disjoint(contract.getEnabledOptions(), option.getForbiddingOptions())){
            contract.getEnabledOptions().add(option);
        }
    }
}
