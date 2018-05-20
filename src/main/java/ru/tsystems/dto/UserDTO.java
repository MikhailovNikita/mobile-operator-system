package ru.tsystems.dto;

import ru.tsystems.persistence.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDTO {


    private String name;
    private String lastName;
    private String email;
    private String password;
    private String passport;
    private String address;
    private Date birthDate;
    private List<ContractDTO> contracts;


    public List<ContractDTO> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractDTO> contracts) {
        this.contracts = contracts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public User toEntity() {
        User client = new User();
        client.setAddress(this.address);
        client.setEmail(this.email);
        client.setName(this.name);
        client.setPassword(this.password);
        client.setLastName(this.lastName);
        client.setPassport(this.passport);
        client.setBirthDate(this.birthDate);
        return client;

    }

    public static UserDTO toDTO(User user){
        /* DO NOT set password in DTO */
        if(Objects.isNull(user)) return null;
        UserDTO dto = new UserDTO();
        dto.setAddress(user.getAddress());
        dto.setBirthDate(user.getBirthDate());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPassport(user.getPassport());
        dto.setLastName(user.getLastName());
        dto.setContracts(user.getContracts().stream().map(ContractDTO::toDTO).collect(Collectors.toList()));
        return dto;
    }
}
