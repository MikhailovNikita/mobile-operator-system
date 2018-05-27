package ru.tsystems.persistence.dao.api;

import ru.tsystems.persistence.entity.User;

import java.util.List;


public interface UserDAO extends GenericDAO<User, Long> {
    User findUserByPassport(String passport);
    List<User> getAllClients();
    User getClientByNumber(String number);
    User findUserByEmail(String email);
}
