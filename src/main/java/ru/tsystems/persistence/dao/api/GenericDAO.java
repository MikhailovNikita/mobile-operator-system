package ru.tsystems.persistence.dao.api;

import java.util.List;


public interface GenericDAO<E, K> {
    void persist(E entity);

    void delete(E entity);

    void update(E entity);

    E get(K id);

    List<E> getAll();
}
