package ru.tsystems.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.TariffDTO;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.dao.api.TariffDAO;
import ru.tsystems.persistence.entity.Tariff;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TariffService {
    @Autowired
    private OptionDAO optionDAO;
    @Autowired
    private TariffDAO tariffDAO;

    public void addNewTariff(TariffDTO tariffDTO) {
        Tariff tariff = TariffDTO.toEntity(tariffDTO);
        tariff.setArchive(false);

        for (String id : tariffDTO.getOptionIds()) {
            tariff.getOption().add(optionDAO.get(Long.valueOf(id)));
        }

        tariffDAO.persist(tariff);
    }


    public List<TariffDTO> getAllTariffs() {
        return tariffDAO.getAll().stream().map(TariffDTO::toDTO).collect(Collectors.toList());
    }

    public List<TariffDTO> getActiveTariffs(){
        return tariffDAO.getActiveTariffs().stream().map(TariffDTO::toDTO).collect(Collectors.toList());
    }

    /**
     * Move tariff to the archive
     * @param id id of the tariff
     */
    public void delete(Long id){
        tariffDAO.get(id).setArchive(true);
    }


    public void changeOptions(Long tariffId, List<Long> optionIds){
        throw new UnsupportedOperationException();
    }
}
