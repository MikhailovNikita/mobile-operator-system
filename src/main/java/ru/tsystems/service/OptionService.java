package ru.tsystems.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.OptionDTO;
import ru.tsystems.exceptions.BusinessLogicException;
import ru.tsystems.exceptions.WrongParameterException;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.entity.Contract;
import ru.tsystems.persistence.entity.TariffOption;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OptionService {
    @Autowired
    private OptionDAO optionDAO;
    @Autowired
    private ContractDAO contractDAO;

    private static final Logger logger = Logger.getLogger(OptionService.class);

    /**
     * Creates new option
     *
     * @param optionDTO specifies title, cost and access cost
     * @throws WrongParameterException if title already taken or cost is negative
     */
    public void addNewOption(OptionDTO optionDTO) {
        logger.info("Creating new option: " + optionDTO.toString());
        if (isOptionWithSuchNamePresent(optionDTO.getName())) {
            throw new WrongParameterException("This title is already taken");
        }
        if (optionDTO.getAccessCost() <= 0 || optionDTO.getCost() < 0) {
            throw new WrongParameterException("Cost can't be negative");
        }
        optionDAO.persist(OptionDTO.toEntity(optionDTO));
    }

    public List<OptionDTO> getAllOptions() {
        logger.debug("Getting all options");
        return optionDAO.getAll().stream().map(OptionDTO::toDTO).collect(Collectors.toList());
    }

    public boolean isOptionWithSuchNamePresent(String name) {
        return (optionDAO.findByName(name) != null);
    }

    /**
     * Creates rule that forbids to enable these two options at one contract
     *
     * @param id1 id of the first option
     * @param id2 id of the second option
     */
    public void addForbiddingOptions(long id1, long id2) {
        logger.info("Creating forbidding rule for options:" + id1 + " and " + id2);
        if (id1 == id2) {
            throw new BusinessLogicException("Equal options are not allowed");
        }
        TariffOption optEntity1 = optionDAO.get(id1);
        TariffOption optEntity2 = optionDAO.get(id2);


        for (Contract contract : contractDAO.getAll()) {
            if (contract.getEnabledOptions().contains(optEntity1)
                    && contract.getEnabledOptions().contains(optEntity2)) {
                throw new BusinessLogicException("Adding this rule invalidates contract(s)");
            }
        }
        if (optEntity1.getForbiddingOptions().contains(optEntity2))
            throw new BusinessLogicException("Rule already exists");
        if (optEntity1.getRequiredOptions().contains(optEntity2) ||
                optEntity2.getRequiredOptions().contains(optEntity1))
            throw new BusinessLogicException("There's an existing requiring rule for these options");
        optEntity1.getForbiddingOptions().add(optEntity2);
        optEntity2.getForbiddingOptions().add(optEntity1);
        optionDAO.update(optEntity1);
        optionDAO.update(optEntity2);
    }

    /**
     * Creates a rule that forbids to enable one option if another is not enabled
     *
     * @param id1 id of the option that will require another option
     * @param id2 id of the option that will be required
     * @throws BusinessLogicException if
     */
    public void addRequiredOption(long id1, long id2) {
        logger.info("Creating requiring rule for options:" + id1 + " and " + id2);
        if (id1 == id2) {
            throw new WrongParameterException("Equal options are not allowed");
        }


        TariffOption optionEntity = optionDAO.get(id1);
        TariffOption requiredOptionEntity = optionDAO.get(id2);

        for (Contract contract : contractDAO.getAll()) {
            if (contract.getEnabledOptions().contains(optionEntity)
                    && !contract.getEnabledOptions().contains(requiredOptionEntity)) {
                throw new BusinessLogicException("Adding this rule invalidates contract(s)");
            }
        }
        if (optionEntity.getRequiredOptions().contains(requiredOptionEntity))
            throw new BusinessLogicException("This rule already exists");
        if (optionEntity.getForbiddingOptions().contains(requiredOptionEntity))
            throw new BusinessLogicException("There's an existing forbidding rule for these options");
        if (isReachable(requiredOptionEntity, optionEntity))
            throw new BusinessLogicException("Rule can not be applied because it creates a cycle");
        optionEntity.getRequiredOptions().add(requiredOptionEntity);
        optionDAO.update(optionEntity);
    }

    /**
     * Returns all forbidding rules
     *
     * @return list of option pairs, where each pair is two options
     * that can't be enabled at one time
     */
    public List<Pair<OptionDTO, OptionDTO>> getForbiddingRules() {
        logger.debug("Getting forbidding rules");
        List<Pair<OptionDTO, OptionDTO>> rules = new ArrayList<>();

        optionDAO.getAll()
                .forEach(x -> x.getForbiddingOptions()
                        .forEach(y -> rules.add(new ImmutablePair<>(OptionDTO.toDTO(x), OptionDTO.toDTO(y)))));

        return rules;
    }

    /**
     * Returns all requiring rules
     *
     * @return list of option pairs, where the first option requires the second
     */
    public List<Pair<OptionDTO, OptionDTO>> getRequiringRules() {
        logger.debug("Getting requring rules");
        List<Pair<OptionDTO, OptionDTO>> rules = new ArrayList<>();
        optionDAO.getAll()
                .forEach(x -> x.getRequiredOptions()
                        .forEach(y -> rules.add(new ImmutablePair<>(OptionDTO.toDTO(x), OptionDTO.toDTO(y)))));
        return rules;
    }

    /**
     * Deletes the forbidding rule
     *
     * @param id1 id of the first option
     * @param id2 id of the second option
     */
    public void deleteForbiddingRule(Long id1, Long id2) {
        logger.info("Deleting forbidding rule" + id1 + " " + id2);
        TariffOption firstOption = optionDAO.get(id1);
        TariffOption secondOption = optionDAO.get(id2);

        if (!firstOption.getForbiddingOptions().contains(secondOption)) {
            throw new BusinessLogicException("This rule doesn't exist");
        }

        firstOption.getForbiddingOptions().remove(secondOption);
        secondOption.getForbiddingOptions().remove(firstOption);
    }

    /**
     * Deletes the requiring rule
     *
     * @param id1 id of the requiring option
     * @param id2 id of the required option
     */
    public void deleteRequiringRule(Long id1, Long id2) {
        logger.info("Deleting forbidding rule:" + id1 + "->" + id2);

        TariffOption firstOption = optionDAO.get(id1);
        TariffOption secondOption = optionDAO.get(id2);
        if (!firstOption.getRequiredOptions().contains(secondOption)) {
            throw new BusinessLogicException("This rule doesn't exist");
        }
        firstOption.getRequiredOptions().remove(secondOption);
    }

    public OptionDTO getOptionById(Long id) {
        return OptionDTO.toDTO(optionDAO.get(id));
    }

    /**
     * Deletes the option from the database
     *
     * @param id id of the option that is being deleted
     */
    public void deleteOptionById(Long id) {
        optionDAO.delete(optionDAO.get(id));
    }

    /**
     * Checks if there's a chain of requring rules
     *
     * @param currentOption start of the chain
     * @param terminal      end of the chain
     * @return true if chain exists, otherwise false
     */
    private boolean isReachable(TariffOption currentOption, TariffOption terminal) {
        for (TariffOption option : currentOption.getRequiredOptions()) {
            if (option.getRequiredOptions().contains(terminal)) {
                return true;
            } else {
                for (TariffOption nextOption : option.getRequiredOptions()) {
                    isReachable(nextOption, terminal);
                }
            }
        }

        return false;
    }


}
