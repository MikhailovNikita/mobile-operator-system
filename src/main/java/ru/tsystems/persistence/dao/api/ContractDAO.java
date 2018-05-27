package ru.tsystems.persistence.dao.api;

import ru.tsystems.persistence.entity.Contract;

import java.util.List;

public interface ContractDAO extends GenericDAO<Contract, Long> {
    Contract getContractByNumber(String number);

    List<Contract> getContractByNumberPattern(String pattern);
}
