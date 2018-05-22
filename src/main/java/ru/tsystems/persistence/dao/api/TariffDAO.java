package ru.tsystems.persistence.dao.api;

import ru.tsystems.persistence.entity.Tariff;

import java.util.List;

public interface TariffDAO extends GenericDAO<Tariff, Long> {
    List<Tariff> getActiveTariffs();

    List<Tariff> getHotTariffs();
}
