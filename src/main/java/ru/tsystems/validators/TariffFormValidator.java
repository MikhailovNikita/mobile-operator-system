package ru.tsystems.validators;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.tsystems.dto.TariffDTO;

public class TariffFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TariffDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TariffDTO tariff = (TariffDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "tariff.name.required");

        if(tariff.getCost() < 0){
            errors.rejectValue("cost", "tariff.cost.negative");
        }
    }
}
