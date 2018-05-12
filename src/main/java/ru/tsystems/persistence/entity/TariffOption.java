package ru.tsystems.persistence.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TariffOption")
@NamedQuery(name = "TariffOption.getAll", query = "SELECT to FROM TariffOption to")
public class TariffOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private Double accessCost;

    @ManyToMany
    @JoinTable(name = "required_options")
    //options that are required for enabling this option
    private Set<TariffOption> requiredOptions = new HashSet<>(0);

    @ManyToMany
    @JoinTable(name = "forbidding_options")
    //options that make enabling this option impossible
    private Set<TariffOption> forbiddingOptions = new HashSet<>(0);

    public TariffOption() {
    }

    public TariffOption(String name, Double cost, Double accessCost, Set<TariffOption> requiredOptions, Set<TariffOption> forbiddingOptions) {
        this.name = name;
        this.cost = cost;
        this.accessCost = accessCost;
        this.requiredOptions = requiredOptions;
        this.forbiddingOptions = forbiddingOptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getAccessCost() {
        return accessCost;
    }

    public void setAccessCost(Double accessCost) {
        this.accessCost = accessCost;
    }

    public Set<TariffOption> getRequiredOptions() {
        return requiredOptions;
    }

    public void setRequiredOptions(Set<TariffOption> requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

    public Set<TariffOption> getForbiddingOptions() {
        return forbiddingOptions;
    }

    public void setForbiddingOptions(Set<TariffOption> forbiddingOptions) {
        this.forbiddingOptions = forbiddingOptions;
    }
}
