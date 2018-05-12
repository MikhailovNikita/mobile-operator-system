package ru.tsystems.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.OptionDTO;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.entity.TariffOption;

import java.util.ArrayList;
import java.util.List;
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

    public boolean doesOptionWithSuchNameExist(String name){
        return (optionDAO.findByName(name) != null);
    }

    public void addForbiddingOptions(String id1, String id2){
        TariffOption optEntity1 = optionDAO.get(Long.valueOf(id1));
        TariffOption optEntity2 = optionDAO.get(Long.valueOf(id2));
        optEntity1.getForbiddingOptions().add(optEntity2);
        optEntity2.getForbiddingOptions().add(optEntity1);
        optionDAO.update(optEntity1);
        optionDAO.update(optEntity2);
    }

    public void addRequiredOption(String id1, String id2){
        TariffOption optionEntity = optionDAO.get(Long.valueOf(id1));
        TariffOption requiredOptionEntity = optionDAO.get(Long.valueOf(id2));
        optionEntity.getRequiredOptions().add(requiredOptionEntity);
        optionDAO.update(optionEntity);
    }

    public List<Pair<OptionDTO, OptionDTO>> getForbiddingRules(){
        List<Pair<OptionDTO, OptionDTO>> rules = new ArrayList<>();
        optionDAO.getAll()
                .forEach(x -> x.getForbiddingOptions()
                            .forEach(y -> rules.add(new ImmutablePair<>(OptionDTO.toDTO(x), OptionDTO.toDTO(y)))));
        return rules;
    }

    public List<Pair<OptionDTO, OptionDTO>> getRequirementRules(){
        List<Pair<OptionDTO, OptionDTO>> rules = new ArrayList<>();
        optionDAO.getAll()
                .forEach(x -> x.getRequiredOptions()
                        .forEach(y -> rules.add(new ImmutablePair<>(OptionDTO.toDTO(x), OptionDTO.toDTO(y)))));
        return rules;
    }

    public void deleteForbiddingRule(Long id1, Long id2){
        TariffOption firstOption = optionDAO.get(id1);
        TariffOption secondOption = optionDAO.get(id2);

        firstOption.getForbiddingOptions().remove(secondOption);
        secondOption.getForbiddingOptions().remove(firstOption);
    }

    public void deleteRequiringRule(Long id1, Long id2){
        TariffOption firstOption = optionDAO.get(id1);
        TariffOption secondOption = optionDAO.get(id2);

        firstOption.getRequiredOptions().remove(secondOption);
    }

    public OptionDTO getOptionById(Long id){
        return OptionDTO.toDTO(optionDAO.get(id));
    }

    public void deleteOptionById(Long id){
        optionDAO.delete(optionDAO.get(id));
    }
}
