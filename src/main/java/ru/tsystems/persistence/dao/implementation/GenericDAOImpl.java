package ru.tsystems.persistence.dao.implementation;

import ru.tsystems.persistence.dao.api.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

//TODO: add exception processing
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
        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            throw e;
        }
    }

    @Override
    public void delete(E entity) {
        try {
            entityManager.remove(entity);
        } catch (PersistenceException e) {

        }
    }

    @Override
    public void update(E entity) {
        try {
            entityManager.merge(entity);
        } catch (PersistenceException e) {
        }
    }

    @Override
    public E get(K id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (PersistenceException e) {

            return null;
        }
    }

    @Override
    public List<E> getAll() {
        try{
            return entityManager.createNamedQuery(entityClass.getSimpleName() + ".getAll", entityClass).getResultList();
        }catch (PersistenceException e){

            return new ArrayList<>(0);
        }

    }
}
