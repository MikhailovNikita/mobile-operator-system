package ru.tsystems.persistence.dao.implementation;

import org.springframework.stereotype.Repository;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.entity.Contract;

@Repository
public class ContractDAOImpl extends GenericDAOImpl<Contract, Long> implements ContractDAO {
    @Override
    public Contract getContractByNumber(String number) {
        return (Contract) entityManager
                .createQuery("SELECT c FROM Contract c WHERE c.number=:number")
                .setParameter("number", number)
                .getSingleResult();
    }
}
