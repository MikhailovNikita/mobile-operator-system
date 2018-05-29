package ru.tsystems.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.dto.UserDTO;
import ru.tsystems.exceptions.WrongParameterException;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.dao.api.UserDAO;
import ru.tsystems.persistence.entity.User;
import ru.tsystems.persistence.entity.UserRole;
import ru.tsystems.utils.PasswordGenerator;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContractDAO contractDAO;

    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$");

    /**
     * Register a new client
     * @param userDTO specifies new client's data such as name, last name and others
     * @return generated password
     */
    public String registerUser(UserDTO userDTO) {
        User user = userDTO.toEntity();
        isUserValid(user);
        user.setRole(UserRole.ROLE_USER);
        user.setBlocked(false);
        String password = PasswordGenerator.generatePassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userDAO.persist(user);
        return password;
    }


    public void isUserValid(User user){
        if(user.getName() == null || user.getLastName() == null){
            throw new WrongParameterException("Name can't be empty");
        }
        if(user.getEmail() == null || !emailPattern.matcher(user.getEmail()).matches()){
            throw new WrongParameterException("Invalid email");
        }
        if(isClientWithSuchEmailPresent(user.getEmail())){
            throw new WrongParameterException("This email is already taken");
        }
        if(user.getAddress() == null || user.getAddress().length() < 5){
            throw new WrongParameterException("Invalid address");
        }
        if(isClientWithSuchPassportPresent(user.getPassport())){
            throw new WrongParameterException("This passport is already registered");
        }
        if(user.getBirthDate() == null){
            throw new WrongParameterException("Invalid birth date");
        }else{
            LocalDate birthDate = user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate curDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(Period.between(birthDate, curDate).getYears() < 18){
                throw new WrongParameterException("Only adults are allowed");
            }

        }
    }


    public UserDTO findUserByEmail(String email) {
        return UserDTO.toDTO(userDAO.findUserByEmail(email));
    }


    public UserDTO findUserByNumber(ContractDTO contractDTO) {
        return UserDTO.toDTO(contractDAO.getContractByNumber(contractDTO.getNumber()).getUser());
    }

    public List<ContractDTO> getAllClientsContracts(String email) {
        User user = userDAO.findUserByEmail(email);
        return user.getContracts().stream().map(ContractDTO::toDTO).collect(Collectors.toList());
    }

    public List<UserDTO> getAllClients(){
        return userDAO.getAllClients().stream().map(UserDTO::toDTO).collect(Collectors.toList());
    }

    public UserDTO getClientById(Long id){
        return UserDTO.toDTO(userDAO.get(id));
    }

    public boolean isClientWithSuchEmailPresent(String email){
        return (userDAO.findUserByEmail(email) != null);
    }

    public boolean isClientWithSuchPassportPresent(String passport){
        return (userDAO.findUserByPassport(passport) != null);
    }
}
