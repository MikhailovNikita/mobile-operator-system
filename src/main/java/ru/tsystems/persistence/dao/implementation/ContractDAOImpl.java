package ru.tsystems.persistence.dao.implementation;

import org.springframework.stereotype.Repository;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.entity.Contract;

import java.util.List;

@Repository
public class ContractDAOImpl extends GenericDAOImpl<Contract, Long> implements ContractDAO {
    @Override
    public Contract getContractByNumber(String number) {
        List<Contract> contracts = entityManager
                .createQuery("SELECT c FROM Contract c WHERE c.number=:number")
                .setParameter("number", number)
                .getResultList();
        if (contracts.size() == 1) return contracts.get(0);
        return null;
    }

    @Override
    public List<Contract> getContractByNumberPattern(String pattern) {
        return entityManager
                .createQuery("SELECT c FROM Contract c where c.number LIKE ?1")
                .setParameter(1, pattern).getResultList();
    }
}