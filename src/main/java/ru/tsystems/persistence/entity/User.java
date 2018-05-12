package ru.tsystems.persistence.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "User")
@NamedQuery(name = "User.getAll", query = "SELECT u FROM User u")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(unique = true)
    private String passport;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean blocked;

    @OneToMany(mappedBy = "user")
    private Set<Contract> contracts;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public User() {
    }

    public User(String name, String lastName, String email, String address, Date birthDate, String passport, String password, Boolean blocked, Set<Contract> contracts, UserRole role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
        this.passport = passport;
        this.password = password;
        this.blocked = blocked;
        this.contracts = contracts;
        this.role = role;
    }
}
