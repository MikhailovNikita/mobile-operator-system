package ru.tsystems.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.OptionDTO;
import ru.tsystems.exceptions.BusinessLogicException;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.entity.TariffOption;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OptionService {
    @Autowired
    private OptionDAO optionDAO;


    public void addNewOption(OptionDTO optionDTO) {
        optionDAO.persist(OptionDTO.toEntity(optionDTO));
    }

    public List<OptionDTO> getAllOptions() {
        return optionDAO.getAll().stream().map(OptionDTO::toDTO).collect(Collectors.toList());
    }

    public boolean doesOptionWithSuchNameExist(String name) {
        return (optionDAO.findByName(name) != null);
    }

    /**
     * Creates rule that forbids to enable these two options at one contract
     * @param id1 id of the first option
     * @param id2 id of the second option
     */
    public void addForbiddingOptions(String id1, String id2) {
        TariffOption optEntity1 = optionDAO.get(Long.valueOf(id1));
        TariffOption optEntity2 = optionDAO.get(Long.valueOf(id2));
        if (optEntity1.getForbiddingOptions().contains(optEntity2)) throw new BusinessLogicException("Rule already exists");
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
     * @param id1 id of the option that will require another option
     * @param id2 id of the option that will be required
     */
    public void addRequiredOption(String id1, String id2) {
        TariffOption optionEntity = optionDAO.get(Long.valueOf(id1));
        TariffOption requiredOptionEntity = optionDAO.get(Long.valueOf(id2));
        if (optionEntity.getRequiredOptions().contains(requiredOptionEntity))
            throw new BusinessLogicException("This rule already exists");
        if (optionEntity.getForbiddingOptions().contains(requiredOptionEntity))
            throw new BusinessLogicException("There's an existing forbidding rule for these options");
        if (isReachable(requiredOptionEntity, optionEntity))
            throw new BusinessLogicException("Rule can not be applied. Reason: ");
        optionEntity.getRequiredOptions().add(requiredOptionEntity);
        optionDAO.update(optionEntity);
    }

    /**
     * Returns all forbidding rules
     * @return list of option pairs, where each pair is two options
     * that can't be enabled at one time
     */
    public List<Pair<OptionDTO, OptionDTO>> getForbiddingRules() {
        List<Pair<OptionDTO, OptionDTO>> rules = new ArrayList<>();
        optionDAO.getAll()
                .forEach(x -> x.getForbiddingOptions()
                        .forEach(y -> rules.add(new ImmutablePair<>(OptionDTO.toDTO(x), OptionDTO.toDTO(y)))));
        return rules;
    }

    /**
     * Returns all requiring rules
     * @return list of option pairs, where the first option requires the second
     */
    public List<Pair<OptionDTO, OptionDTO>> getRequirementRules() {
        List<Pair<OptionDTO, OptionDTO>> rules = new ArrayList<>();
        optionDAO.getAll()
                .forEach(x -> x.getRequiredOptions()
                        .forEach(y -> rules.add(new ImmutablePair<>(OptionDTO.toDTO(x), OptionDTO.toDTO(y)))));
        return rules;
    }

    /**
     * Deletes the forbidding rule
     * @param id1 id of the first option
     * @param id2 id of the second option
     */
    public void deleteForbiddingRule(Long id1, Long id2) {
        TariffOption firstOption = optionDAO.get(id1);
        TariffOption secondOption = optionDAO.get(id2);

        firstOption.getForbiddingOptions().remove(secondOption);
        secondOption.getForbiddingOptions().remove(firstOption);
    }

    /**
     * Deletes the requiring rule
     * @param id1 id of the requiring option
     * @param id2 id of the required option
     */
    public void deleteRequiringRule(Long id1, Long id2) {
        TariffOption firstOption = optionDAO.get(id1);
        TariffOption secondOption = optionDAO.get(id2);

        firstOption.getRequiredOptions().remove(secondOption);
    }

    public OptionDTO getOptionById(Long id) {
        return OptionDTO.toDTO(optionDAO.get(id));
    }

    /**
     * Deletes the option from the database
     * @param id id of the option that is being deleted
     */
    public void deleteOptionById(Long id) {
        optionDAO.delete(optionDAO.get(id));
    }

    /**
     *
     * @param currentOption
     * @param terminal
     * @return
     */
    public boolean isReachable(TariffOption currentOption, TariffOption terminal) {
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
