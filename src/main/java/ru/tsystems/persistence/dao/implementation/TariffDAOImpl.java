package ru.tsystems.persistence.dao.implementation;

import org.springframework.stereotype.Repository;
import ru.tsystems.persistence.dao.api.TariffDAO;
import ru.tsystems.persistence.entity.Tariff;

import java.util.List;


@Repository
public class TariffDAOImpl extends GenericDAOImpl<Tariff, Long> implements TariffDAO {
    @Override
    public List<Tariff> getActiveTariffs() {
        return entityManager.createQuery("SELECT t FROM Tariff t WHERE t.archive = FALSE")
                .getResultList();
    }

    @Override
    public List<Tariff> getHotTariffs() {
        return entityManager.createQuery("SELECT t FROM Tariff t WHERE t.hot = TRUE")
                .getResultList();
    }

    @Override
    public Tariff getByName(String name) {
        List<Tariff> tariffs = entityManager.createQuery("SELECT t FROM Tariff t WHERE t.name=:name")
                .setParameter("name", name).getResultList();
        if(tariffs.isEmpty()) return null;
        return tariffs.get(0);
    }
}
