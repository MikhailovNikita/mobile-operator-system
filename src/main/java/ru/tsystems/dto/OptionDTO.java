package ru.tsystems.dto;

import ru.tsystems.persistence.entity.TariffOption;


public class OptionDTO {

    private String id;
    private String name;
    private double cost;
    private double accessCost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public double getAccessCost() {
        return accessCost;
    }

    public void setAccessCost(Double accessCost) {
        this.accessCost = accessCost;
    }

    public static TariffOption toEntity(OptionDTO optionDTO) {
        TariffOption option = new TariffOption();
        option.setCost(optionDTO.cost);
        option.setName(optionDTO.name);
        option.setAccessCost(optionDTO.accessCost);
        if(optionDTO.getId() != null){
            option.setId(Long.valueOf(optionDTO.getId()));
        }

        return option;
    }

    public static OptionDTO toDTO(TariffOption option) {
        OptionDTO dto = new OptionDTO();
        dto.setName(option.getName());
        dto.setId(option.getId().toString());
        dto.setCost(option.getCost());
        dto.setAccessCost(option.getAccessCost());

        return dto;
    }

    @Override
    public String toString() {
        return "OptionDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", accessCost=" + accessCost +
                '}';
    }
}
