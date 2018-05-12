package ru.tsystems.persistence.dao.implementation;

import org.springframework.stereotype.Repository;
import ru.tsystems.persistence.dao.api.UserDAO;
import ru.tsystems.persistence.entity.User;
import ru.tsystems.persistence.entity.UserRole;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {
    @Override
    public User findUserByPassport(String passport) {
        try{
            return (User) entityManager
                    .createQuery("SELECT u FROM User u WHERE u.passport=:passport")
                    .setParameter("passport", passport)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }


    }

    @Override
    public User findUserByEmail(String email) {
        try{
            return (User) entityManager
                    .createQuery("SELECT u FROM User u WHERE u.email=:email")
                    .setParameter("email", email)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }

    }

    public List<User> getAllClients() {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.role=:role")
                .setParameter("role", UserRole.ROLE_USER)
                .getResultList();
    }


    public User getClientByNumber(String number) {
        try{
            return (User) entityManager
                    .createQuery("SELECT u FROM User u JOIN u.contracts c WHERE c.number=:number")
                   .setParameter("number", number)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
}
