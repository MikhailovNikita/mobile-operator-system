package ru.tsystems.service;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.TariffDTO;
import ru.tsystems.exceptions.BusinessLogicException;
import ru.tsystems.exceptions.WrongParameterException;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.dao.api.TariffDAO;
import ru.tsystems.persistence.entity.Contract;
import ru.tsystems.persistence.entity.Tariff;
import ru.tsystems.persistence.entity.TariffOption;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TariffService {
    @Autowired
    private OptionDAO optionDAO;
    @Autowired
    private TariffDAO tariffDAO;
    @Autowired
    private ContractDAO contractDAO;

    private static final Logger logger = Logger.getLogger(TariffService.class);

    /**
     * Creates new tariff
     * @param tariffDTO specifies title, cost and supported options
     * @throws WrongParameterException if some parameters violate constraints
     */
    public void addNewTariff(TariffDTO tariffDTO) {
        logger.info("Creating tariff from DTO: " + tariffDTO.toString());
        Tariff tariff = TariffDTO.toEntity(tariffDTO);
        tariff.setArchive(false);
        if(isTariffWithSuchNamePresent(tariff.getName())){
            throw new WrongParameterException("This title is already taken");
        }
        if(tariff.getCost() <= 0 ){
            throw new WrongParameterException("Cost must be a positive number");
        }
        for (String id : tariffDTO.getOptionIds()) {
            tariff.getOption().add(optionDAO.get(Long.valueOf(id)));
        }

        logger.info("Data for new tariff is valid");
        tariffDAO.persist(tariff);
    }


    public List<TariffDTO> getAllTariffs() {
        logger.debug("Getting all tariffs");
        return tariffDAO.getAll().stream().map(TariffDTO::toDTO).collect(Collectors.toList());
    }

    public List<TariffDTO> getActiveTariffs() {
        logger.debug("Getting all active tariffs");
        return tariffDAO.getActiveTariffs().stream().map(TariffDTO::toDTO).collect(Collectors.toList());
    }

    /**
     * Move tariff to the archive
     *
     * @param id id of the tariff
     */
    public void archive(Long id) {
        logger.info("Archiving tariff with id" + id);
        Tariff tariff = tariffDAO.get(id);
        tariff.setArchive(true);
        tariff.setHot(false);
    }

    public TariffDTO get(Long id) {
        return TariffDTO.toDTO(tariffDAO.get(id));
    }

    /**
     * Updates supported options of tariff
     * @param tariffDTO specified id and list of options
     * @throws BusinessLogicException if new list of options violates existing contracts
     */
    public void updateOptions(TariffDTO tariffDTO){
        logger.info("Updating tariff: " + tariffDTO);

        List<Contract> contracts = contractDAO.getAll();
        HashSet<TariffOption> newOptions = new HashSet<>();
        Tariff tariff = tariffDAO.get(tariffDTO.getId());

        for(String optionId : tariffDTO.getOptionIds()){
            newOptions.add(optionDAO.get(Long.valueOf(optionId)));
        }

        for(Contract contract : contracts){
            if(contract.getTariff().equals(tariff)){
                for(TariffOption enabledOption : contract.getEnabledOptions()){
                    if(!newOptions.contains(enabledOption)){
                        throw new BusinessLogicException("Update can not be executed: " +
                                "some contracts have options that are not included in new list");
                    }
                }
            }
        }
    }

    public List<TariffDTO> getHotTariffs(){
        logger.debug("Getting hot tariffs");
        return tariffDAO.getHotTariffs().stream().map(TariffDTO::toDTO).collect(Collectors.toList());
    }

    public void changeHotStatus(Long id){
        logger.info("Changing hot status of tariff with id " +  id);
        Tariff tariff = tariffDAO.get(id);
        if(tariff.getArchive()){
            throw new BusinessLogicException("This tariff is archived");
        }

        tariff.setHot(!tariff.getHot());
    }

    public boolean isTariffWithSuchNamePresent(String title){
        return tariffDAO.getByName(title) != null;
    }
}
