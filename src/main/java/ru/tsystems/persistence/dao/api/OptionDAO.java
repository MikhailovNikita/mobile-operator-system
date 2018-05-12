package ru.tsystems.persistence.dao.api;

import ru.tsystems.persistence.entity.TariffOption;

public interface OptionDAO extends GenericDAO<TariffOption, Long> {
    public TariffOption findByName(String name);
}
