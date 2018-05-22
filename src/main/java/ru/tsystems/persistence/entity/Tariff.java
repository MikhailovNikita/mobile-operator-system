package ru.tsystems.persistence.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Tariff")
@NamedQuery(name = "Tariff.getAll", query = "SELECT t FROM Tariff t")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private Boolean archive;

    @Column(nullable = false)
    private Boolean hot;

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    @ManyToMany
    private Set<TariffOption> option = new HashSet<>();

    @OneToMany
    private Set<Contract> contract = new HashSet<>();

    public Tariff() {
    }

    public Tariff(String name, Double cost, Boolean archive, Set<TariffOption> option, Set<Contract> contract) {
        this.name = name;
        this.cost = cost;
        this.archive = archive;
        this.option = option;
        this.contract = contract;
        this.hot = false;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TariffOption> getOption() {
        return option;
    }

    public void setOption(Set<TariffOption> option) {
        this.option = option;
    }

    public Set<Contract> getContract() {
        return contract;
    }

    public void setContract(Set<Contract> contract) {
        this.contract = contract;
    }
}
