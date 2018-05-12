package ru.tsystems.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.service.UserService;

public class ContractFormValidator implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return ContractDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ContractDTO contract = (ContractDTO) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "contract.number.required");

        if(!userService.doesClientWithSuchPassportExist(contract.getOwnersPassport())){
            errors.rejectValue("ownersPassport", "contract.passport.invalid");
        }
    }
}
