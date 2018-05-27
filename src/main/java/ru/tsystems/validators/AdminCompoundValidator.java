package ru.tsystems.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class AdminCompoundValidator implements Validator {

    private List<Validator> validators;

    public AdminCompoundValidator(List<Validator> validators) {
        super();
        this.validators = validators;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        for(Validator v : validators){
            if(v.supports(aClass)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        for(Validator v : validators){
            if(v.supports(o.getClass())){
                v.validate(o, errors);
            }
        }
    }
}
