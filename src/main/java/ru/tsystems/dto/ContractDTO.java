package ru.tsystems.dto;


import ru.tsystems.persistence.entity.Contract;
import ru.tsystems.persistence.entity.TariffOption;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ContractDTO {
    private String contractId;
    private String ownersPassport;
    private String number;
    private Double monthlyPayment;
    private TariffDTO tariffDTO;
    private String tariffName;
    private Boolean blockedByUser;
    private Boolean blockedByAdmin;
    private Date conclusionDate;
    private List<OptionDTO> options;


    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }

    public Date getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(Date conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public String getOwnersPassport() {
        return ownersPassport;
    }

    public void setOwnersPassport(String ownersPassport) {
        this.ownersPassport = ownersPassport;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public TariffDTO getTariffDTO() {
        return tariffDTO;
    }

    public void setTariffDTO(TariffDTO tariffDTO) {
        this.tariffDTO = tariffDTO;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Boolean getBlockedByUser() {
        return blockedByUser;
    }

    public void setBlockedByUser(Boolean blockedByUser) {
        this.blockedByUser = blockedByUser;
    }

    public Boolean getBlockedByAdmin() {
        return blockedByAdmin;
    }

    public void setBlockedByAdmin(Boolean blockedByAdmin) {
        this.blockedByAdmin = blockedByAdmin;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    @Override
    public String toString() {
        return "ContractDTO{" +
                "ownersPassport='" + ownersPassport + '\'' +
                ", number='" + number + '\'' +
                ", tariffId=" + (tariffDTO != null ? tariffDTO.getId() : null) +
                '}';
    }


    public static ContractDTO toDTO(Contract contract) {
        ContractDTO dto = new ContractDTO();
        dto.setNumber(contract.getNumber());
        dto.setTariffDTO(TariffDTO.toDTO(contract.getTariff()));
        dto.setContractId(contract.getId().toString());
        dto.setBlockedByUser(contract.isBlockedByUser());
        dto.setBlockedByAdmin(contract.isBlockedByAdmin());
        dto.setTariffName(contract.getTariff().getName());
        dto.setConclusionDate(contract.getConclusionDate());
        dto.setOptions(contract
                .getEnabledOptions()
                .stream()
                .map(OptionDTO::toDTO)
                .collect(Collectors.toList()));
        dto.setMonthlyPayment(contract.getTariff().getCost() +
                contract.getEnabledOptions()
                        .stream()
                        .mapToDouble(TariffOption::getCost)
                        .sum());
        return dto;
    }


}
