package ru.tsystems.persistence.dao.implementation;

import ru.tsystems.persistence.dao.api.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class GenericDAOImpl<E, K> implements GenericDAO<E, K> {
    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;


    @SuppressWarnings("unchecked")
    GenericDAOImpl() {
        entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public void persist(E entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public E get(K id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<E> getAll() {
        return entityManager.createNamedQuery(entityClass.getSimpleName() + ".getAll", entityClass).getResultList();
    }
}
