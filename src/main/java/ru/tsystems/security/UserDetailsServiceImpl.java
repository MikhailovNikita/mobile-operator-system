package ru.tsystems.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.tsystems.persistence.dao.api.UserDAO;
import ru.tsystems.persistence.entity.User;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    //email is used as username
    public UserDetails loadUserByUsername(String s){
        User user = userDAO.findUserByEmail(s);
        if(user == null){
            throw new UsernameNotFoundException("User with username " + s + " not found.");
        }
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(s);
        builder.disabled(user.getBlocked());
        builder.password(user.getPassword());
        builder.authorities(user.getRole().name());


        return builder.build();
    }
}
