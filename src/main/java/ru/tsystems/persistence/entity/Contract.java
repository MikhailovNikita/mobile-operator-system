package ru.tsystems.persistence.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Contract")
@NamedQuery(name = "Contract.getAll", query = "SELECT c FROM Contract c")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    @ManyToOne
    private Tariff tariff;

    @ManyToMany
    @JoinTable(name = "contract_option",
                joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
    private Set<TariffOption> enabledOptions = new HashSet<>(0);

    @ManyToOne
    private User user;

    @Column
    private boolean blockedByUser;

    @Column
    private boolean blockedByAdmin;

    @Column
    @Temporal(TemporalType.DATE)
    private Date conclusionDate;

    public Contract() {
    }

    public Contract(String number, Tariff tariff, Set<TariffOption> enabledOptions, User user, boolean blockedByUser, boolean blockedByAdmin, Date conclusionDate) {
        this.number = number;
        this.tariff = tariff;
        this.enabledOptions = enabledOptions;
        this.user = user;
        this.blockedByUser = blockedByUser;
        this.blockedByAdmin = blockedByAdmin;
        this.conclusionDate = conclusionDate;
    }

    public Date getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(Date conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public boolean isBlockedByUser() {
        return blockedByUser;
    }

    public void setBlockedByUser(boolean blockedByUser) {
        this.blockedByUser = blockedByUser;
    }

    public boolean isBlockedByAdmin() {
        return blockedByAdmin;
    }

    public void setBlockedByAdmin(boolean blockedByAdmin) {
        this.blockedByAdmin = blockedByAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Set<TariffOption> getEnabledOptions() {
        return enabledOptions;
    }

    public void setEnabledOptions(Set<TariffOption> enabledOptions) {
        this.enabledOptions = enabledOptions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
