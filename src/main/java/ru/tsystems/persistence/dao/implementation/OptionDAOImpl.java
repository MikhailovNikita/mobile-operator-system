package ru.tsystems.persistence.dao.implementation;

import org.springframework.stereotype.Repository;
import ru.tsystems.persistence.dao.api.OptionDAO;
import ru.tsystems.persistence.entity.TariffOption;

import javax.persistence.NoResultException;

@Repository
public class OptionDAOImpl extends GenericDAOImpl<TariffOption, Long> implements OptionDAO {
    @Override
    public TariffOption findByName(String name) {
        try{
            return (TariffOption) entityManager.createQuery("SELECT to FROM TariffOption to WHERE to.name=:name")
                    .setParameter("name", name)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }

    }
}
