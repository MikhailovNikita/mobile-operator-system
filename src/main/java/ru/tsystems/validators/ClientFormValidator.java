package ru.tsystems.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.tsystems.dto.UserDTO;
import ru.tsystems.service.UserService;

public class ClientFormValidator implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO user = (UserDTO) o;

        //name validation
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "client.name.required");

        //last name validation
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "client.lastName.required");

        //passport validation
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passport", "client.passport.required)");
        if(userService.doesClientWithSuchPassportExist(user.getPassport())){
            errors.rejectValue("passport", "client.passport.taken");
        }

        //birthday validation
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "client.birthdate.required");

        //email validation
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "client.email.required");
        if(userService.doesClientWithSuchEmailExist(user.getEmail())){
            errors.rejectValue("email", "client.email.taken");
        }
    }
}
