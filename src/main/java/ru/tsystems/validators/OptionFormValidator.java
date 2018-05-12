package ru.tsystems.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.tsystems.dto.OptionDTO;

public class OptionFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return OptionDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OptionDTO option = (OptionDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "option.name.required");

        if(option.getCost() < 0){
            errors.rejectValue("cost", "option.cost.negative");
        }

        if(option.getAccessCost() < 0){
            errors.rejectValue("cost", "option.accessCost.negative");
        }


    }
}
