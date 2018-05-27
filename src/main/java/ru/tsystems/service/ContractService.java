package ru.tsystems.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.dto.OptionDTO;
import ru.tsystems.exceptions.BusinessLogicException;
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

    private final static Logger logger = LogManager.getLogger(ContractService.class);

    /**
     * Checks if number is already taken
     * @param number specified number
     * @return true if this number is already taken, otherwise false
     */
    public boolean isContractWithSuchNumberExist(String number) {
        return (contractDAO.getContractByNumber(number) != null);
    }


    /**
     * Creates new contract with specified tariff, owner, phone number and current date
     *
     * @param contractDTO not empty number that fits pattern,
     *                    passport number of existing client,
     *                    id of existing, non-archived tariff.
     * @throws BusinessLogicException if user with such passport doesn't exist.
     */
    public void addNewContract(ContractDTO contractDTO) {
        Tariff tariff = tariffDAO.get(contractDTO.getTariffDTO().getId());
        User user = userDAO.findUserByPassport(contractDTO.getOwnersPassport());
        if (user == null) {
            throw new BusinessLogicException("No user with such passport");
        }
        if(isContractWithSuchNumberExist(contractDTO.getNumber())){
            throw new BusinessLogicException("This number is already taken");
        }
        Contract contract = new Contract();
        contract.setTariff(tariff);
        contract.setUser(user);
        contract.setNumber(contractDTO.getNumber());
        contract.setConclusionDate(new Date());
        logger.debug("reached here");
        contractDAO.persist(contract);
    }

    /**
     * Finds contract by it's id
     *
     * @param id contract's identifier
     * @return contract transformed in DTO
     */
    public ContractDTO findContractById(Long id) {
        return ContractDTO.toDTO(contractDAO.get(id));
    }


    public ContractDTO findContractByNumber(String number) {
        return ContractDTO.toDTO(contractDAO.getContractByNumber(number));
    }

    public List<ContractDTO> findContractByNumberPattern(String pattern) {
        return contractDAO
                .getContractByNumberPattern(pattern + "%")
                .stream()
                .map(ContractDTO::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Returns  all contracts
     * @return List of contracts transformed to DTO
     */
    public List<ContractDTO> getAllContracts() {
        return contractDAO.getAll().stream().map(ContractDTO::toDTO).collect(Collectors.toList());
    }

    /**
     * Sets contract to blocked-by-user status
     * @param id defines contract by id
     * @throws BusinessLogicException if contract is blocked by operator
     */
    public void userBlocksContract(Long id) {
        Contract contract = contractDAO.get(id);
        if(contract.isBlockedByAdmin()) throw new BusinessLogicException("Contract is blocked by operator");
        contract.setBlockedByUser(true);
        contractDAO.update(contract);
    }

    /**
     * Sets contract to not-blocked-by-user status
     * @param id defines contract by id
     * @throws BusinessLogicException if contract is blocked by operator
     */
    public void userUnblocksContract(Long id) {
        Contract contract = contractDAO.get(id);
        if(contract.isBlockedByAdmin()) throw new BusinessLogicException("Contract is blocked by operator");
        contract.setBlockedByUser(false);
        contractDAO.update(contract);
    }

    /**
     * Sets contract to blocked-by-operator status
     * @param id defines contract by id
     * @throws BusinessLogicException if contract is already blocked by operator
     */
    public void adminBlocksContract(Long id) {
        Contract contract = contractDAO.get(id);
        if(contract.isBlockedByAdmin()) throw new BusinessLogicException("Contract is already blocked");
        contract.setBlockedByAdmin(true);
        contractDAO.update(contract);
    }

    /**
     * Sets contract to not-blocked-by-operator status
     * @param id defines contract by id
     * @throws BusinessLogicException if contract is not blocked
     */
    public void adminUnblocksContract(Long id) {
        Contract contract = contractDAO.get(id);
        if(!contract.isBlockedByAdmin()) throw new BusinessLogicException("This contract is not blocked");
        contract.setBlockedByAdmin(false);
        contractDAO.update(contract);
    }

    /**
     * Changes tariff of specified contract
     * Disables all options that are not supported by new tariff
     * @param contractId specifies contract
     * @param tariffId specifies tariff
     */
    public void changeTariff(Long contractId, Long tariffId) {
        Contract contract = contractDAO.get(contractId);
        if (contract.isBlockedByAdmin() || contract.isBlockedByUser()) return;

        Tariff tariff = tariffDAO.get(tariffId);
        contract.setTariff(tariff);

        contract.setEnabledOptions(new HashSet<>(
                contract.getEnabledOptions().stream()
                        .filter(x -> tariff.getOption().contains(x))
                        .collect(Collectors.toList())));
    }

    /**
     * Disables option of specified contract
     * Also disables all enabled options that require options that is being disabled
     * @param contractId specifies contract
     * @param optionId specifies option
     */
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

        List<OptionDTO> options = contract.getTariff().getOption().stream()
                .filter(x -> !enabledOptions.contains(x))
                .filter(x -> contract.getTariff().getOption().contains(x))
                .filter(x -> contract.getEnabledOptions().containsAll(x.getRequiredOptions()))
                .filter(x -> Collections.disjoint(x.getForbiddingOptions(), contract.getEnabledOptions()))
                .map(OptionDTO::toDTO)
                .collect(Collectors.toList());

        return options;
    }

    /**
     * Enables option for specified contract
     * @param contractId
     * @param optionId
     */
    public void enableOption(Long contractId, Long optionId) {
        Contract contract = contractDAO.get(contractId);
        TariffOption option = optionDAO.get(optionId);

        if (contract.getEnabledOptions().containsAll(option.getRequiredOptions())
                && Collections.disjoint(contract.getEnabledOptions(), option.getForbiddingOptions())) {
            contract.getEnabledOptions().add(option);
        }
    }
}
