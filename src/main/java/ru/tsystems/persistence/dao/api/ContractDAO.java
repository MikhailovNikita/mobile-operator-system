package ru.tsystems.persistence.dao.api;

import ru.tsystems.persistence.entity.Contract;

public interface ContractDAO extends GenericDAO<Contract, Long> {
    public Contract getContractByNumber(String number);
}
