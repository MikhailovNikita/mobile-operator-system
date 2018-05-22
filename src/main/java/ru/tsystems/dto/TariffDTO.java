package ru.tsystems.dto;

import ru.tsystems.persistence.entity.Tariff;


public class TariffDTO  {

    private String[] optionIds;
    private String name;
    private Double cost;
    private Long id;
    private boolean archive;
    private boolean hot;

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public String[] getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(String[] optionIds) {
        this.optionIds = optionIds;
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

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public static TariffDTO toDTO(Tariff tariff){
         TariffDTO dto = new TariffDTO();
         dto.setCost(tariff.getCost());
         dto.setName(tariff.getName());
         dto.setId(tariff.getId());
         dto.setHot(tariff.getHot());
         return dto;
    }

    public static Tariff toEntity(TariffDTO dto){
        Tariff tariff = new Tariff();
        tariff.setName(dto.getName());
        tariff.setCost(dto.getCost());
        tariff.setId(dto.getId());
        tariff.setHot(dto.isHot());
        return tariff;
    }
}
