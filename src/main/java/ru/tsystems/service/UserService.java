package ru.tsystems.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.dto.UserDTO;
import ru.tsystems.persistence.dao.api.ContractDAO;
import ru.tsystems.persistence.dao.api.UserDAO;
import ru.tsystems.persistence.entity.Contract;
import ru.tsystems.persistence.entity.User;
import ru.tsystems.persistence.entity.UserRole;
import ru.tsystems.utils.EmailNotification;
import ru.tsystems.utils.PasswordGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContractDAO contractDAO;

    private static final Logger logger = Logger.getLogger(UserService.class);

    public void registerUser(UserDTO userDTO) {
        User user = userDTO.toEntity();
        user.setBlocked(false);
        user.setRole(UserRole.ROLE_USER);
        String password = PasswordGenerator.generatePassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        logger.info("Generated password " + password + " for user " + user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userDAO.persist(user);
        EmailNotification.sendPassword(user.getEmail(), user.getName() + " " + user.getLastName(),
                password);
    }

    public UserDTO findUserByEmail(String email) {
        return UserDTO.toDTO(userDAO.findUserByEmail(email));
    }

    public void blockContract(ContractDTO contractDTO) {
        Contract contract = contractDAO.getContractByNumber(contractDTO.getNumber());
        contract.setBlockedByAdmin(true);
        contractDAO.update(contract);
    }

    public void unblockContract(ContractDTO contractDTO){
        Contract contract = contractDAO.getContractByNumber(contractDTO.getNumber());
        contract.setBlockedByAdmin(false);
        contractDAO.update(contract);
    }

    public UserDTO findUserByNumber(ContractDTO contractDTO) {
        return UserDTO.toDTO(contractDAO.getContractByNumber(contractDTO.getNumber()).getUser());
    }

    public List<ContractDTO> getAllClientsContracts(String email) {
        User user = userDAO.findUserByEmail(email);
        logger.debug(user.getContracts().size() + " contracts found for user " + user.getEmail());
        return user.getContracts().stream().map(ContractDTO::toDTO).collect(Collectors.toList());
    }

    public List<UserDTO> getAllClients(){
        return userDAO.getAllClients().stream().map(UserDTO::toDTO).collect(Collectors.toList());
    }

    public UserDTO getClientById(Long id){
        return UserDTO.toDTO(userDAO.get(id));
    }


    public UserDTO getClientByNumber(String number){
        return UserDTO.toDTO(userDAO.getClientByNumber(number));
    }

    public boolean doesClientWithSuchEmailExist(String email){
        return (userDAO.findUserByEmail(email) != null);
    }

    public boolean doesClientWithSuchPassportExist(String passport){
        return (userDAO.findUserByPassport(passport) != null);
    }
}
