package ru.tsystems.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.exceptions.BusinessLogicException;
import ru.tsystems.exceptions.WrongParameterException;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.dao.api.TariffDAO;
import ru.tsystems.persistence.dao.api.UserDAO;
import ru.tsystems.persistence.entity.Contract;
import ru.tsystems.persistence.entity.Tariff;
import ru.tsystems.persistence.entity.TariffOption;
import ru.tsystems.persistence.entity.User;

import java.util.*;
import java.util.regex.Pattern;
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

    private static final String CONTRACT_IS_BLOCKED = "Contract is blocked";
    private static final Logger logger = Logger.getLogger(ContractService.class);

    /**
     * Checks if number is already taken
     *
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
        logger.info("Creating new contract: " + contractDTO.toString());
        if(contractDTO.getTariffDTO() == null || contractDTO.getTariffDTO().getId() == null){
            throw new WrongParameterException("Invalid tariff");
        }
        Tariff tariff = tariffDAO.get(contractDTO.getTariffDTO().getId());

        User user = userDAO.findUserByPassport(contractDTO.getOwnersPassport());
        if (user == null) {
            throw new WrongParameterException("No user with such passport");
        }
        if (contractDTO.getNumber() == null || isContractWithSuchNumberExist(contractDTO.getNumber())) {
            throw new WrongParameterException("This number is already taken");
        }

        Pattern p = Pattern.compile("[8][0-9]{1,3}[\\(][0-9]{3}[\\)][0-9]{7}");
        if(!p.matcher(contractDTO.getNumber()).matches()){
            throw new WrongParameterException("Wrong number format");
        }

        if (contractDTO.getTariffDTO() == null || contractDTO.getTariffDTO().getId() == null) {
            throw new WrongParameterException("Please, choose a tariff");
        }
        Contract contract = new Contract();
        contract.setTariff(tariff);
        contract.setUser(user);
        contract.setNumber(contractDTO.getNumber());
        contract.setConclusionDate(new Date());
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


    /**
     * Finds contracts by number
     *
     * @param number key number
     * @return found contract in DTO form
     * @throws BusinessLogicException in case if there's no contracts with such number
     */
    public ContractDTO findContractByNumber(String number) {
        logger.debug("Finding contract by number" + number);
        if (!isContractWithSuchNumberExist(number)) {
            throw new BusinessLogicException("No contract with such number");
        }
        return ContractDTO.toDTO(contractDAO.getContractByNumber(number));
    }

    /**
     * Finds contract by number's prefix
     *
     * @param pattern prefix of number
     * @return list of matching contracts in DTO form
     */
    public List<ContractDTO> findContractByNumberPattern(String pattern) {
        logger.debug("Finding contract(s) by number prefix" + pattern);
        return contractDAO
                .getContractByNumberPattern(pattern + "%")
                .stream()
                .map(ContractDTO::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Returns  all contracts
     *
     * @return List of contracts transformed to DTO
     */
    public List<ContractDTO> getAllContracts() {
        return contractDAO.getAll().stream().map(ContractDTO::toDTO).collect(Collectors.toList());
    }

    /**
     * Sets contract to blocked-by-user status
     *
     * @param id defines contract by id
     * @throws BusinessLogicException if contract is blocked by operator
     */
    public void userBlocksContract(Long id) {
        logger.info("User attempts to block contract with id: " + id);
        Contract contract = contractDAO.get(id);
        if (contract.isBlockedByAdmin()) throw new BusinessLogicException("Contract is blocked by operator");
        contract.setBlockedByUser(true);
        contractDAO.update(contract);
    }

    /**
     * Sets contract to not-blocked-by-user status
     *
     * @param id defines contract by id
     * @throws BusinessLogicException if contract is blocked by operator
     */
    public void userUnblocksContract(Long id) {
        logger.info("User attempts to unblock contract with id: " + id);
        Contract contract = contractDAO.get(id);
        if (contract.isBlockedByAdmin()) throw new BusinessLogicException("Contract is blocked by operator");
        contract.setBlockedByUser(false);
        contractDAO.update(contract);
    }

    /**
     * Sets contract to blocked-by-operator status
     *
     * @param id defines contract by id
     * @throws BusinessLogicException if contract is already blocked by operator
     */
    public void adminBlocksContract(Long id) {
        logger.info("Admin attempts to block contract with id: " + id);
        Contract contract = contractDAO.get(id);
        if (contract.isBlockedByAdmin()) throw new BusinessLogicException("Contract is already blocked");
        contract.setBlockedByAdmin(true);
        contractDAO.update(contract);
    }

    /**
     * Sets contract to not-blocked-by-operator status
     *
     * @param id defines contract by id
     * @throws BusinessLogicException if contract is not blocked
     */
    public void adminUnblocksContract(Long id) {
        logger.info("Admin attempts to unblock contract with id: " + id);
        Contract contract = contractDAO.get(id);
        if (!contract.isBlockedByAdmin()) throw new BusinessLogicException("This contract is not blocked");
        contract.setBlockedByAdmin(false);
        contractDAO.update(contract);
    }

    /**
     * Changes tariff of specified contract
     * Disables all options that are not supported by new tariff
     *
     * @param contractId specifies contract
     * @param tariffId   specifies tariff
     */
    public void changeTariff(Long contractId, Long tariffId) {
        Contract contract = contractDAO.get(contractId);
        if (contract.isBlockedByAdmin() || contract.isBlockedByUser()) {
            throw new BusinessLogicException(CONTRACT_IS_BLOCKED);
        }

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
     *
     * @param contractId specifies contract
     * @param optionId   specifies option
     */
    public void disableOption(Long contractId, Long optionId) {
        Contract contract = contractDAO.get(contractId);
        if (contract.isBlockedByAdmin() || contract.isBlockedByUser()) {
            throw new BusinessLogicException(CONTRACT_IS_BLOCKED);
        }
        TariffOption option = optionDAO.get(optionId);
        contract.getEnabledOptions().remove(option);
        contract.setEnabledOptions(new HashSet<>(
                contract.getEnabledOptions().stream()
                        .filter(x -> !x.getRequiredOptions().contains(option))
                        .collect(Collectors.toList())));

    }

    /**
     * Enables option for specified contract
     *
     * @param contractId id of the contract
     * @param optionId   id of the option
     * @throw BusinessLogicException if options is not supported by tariff
     * or some rules are not followed
     */
    public void enableOption(Long contractId, Long optionId) {
        Contract contract = contractDAO.get(contractId);
        TariffOption option = optionDAO.get(optionId);
        if (contract.isBlockedByUser() || contract.isBlockedByAdmin()) {
            throw new BusinessLogicException(CONTRACT_IS_BLOCKED);
        }
        //Checking that tariff supports this option
        if (!contract.getTariff().getOption().contains(option)) {
            throw new BusinessLogicException("Option is not supported by current tariff");
        }

        //Checking that all required options are present
        HashSet<TariffOption> temp = new HashSet<>();
        temp.addAll(option.getRequiredOptions());
        temp.removeAll(contract.getEnabledOptions());
        //some required options are not present
        if (!temp.isEmpty()) {
            throw new BusinessLogicException("This option requires other options" +
                    " to be enabled. Please enable following options: " +
                    temp.stream().map(TariffOption::getName).collect(Collectors.joining(", ")));
        }

        //Checking that forbidding options are not enabled
        temp = new HashSet<>();
        temp.addAll(option.getForbiddingOptions());
        temp.retainAll(contract.getEnabledOptions());

        if (!temp.isEmpty()) {
            throw new BusinessLogicException("Following options forbid enabling this option." +
                    temp.stream().map(TariffOption::getName).collect(Collectors.joining(", ")));
        }

        contract.getEnabledOptions().add(option);
    }
}
