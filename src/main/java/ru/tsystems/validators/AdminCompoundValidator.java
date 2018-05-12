package ru.tsystems.validators;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class AdminCompoundValidator implements Validator {
    private static final Logger logger = Logger.getLogger(AdminCompoundValidator.class);

    private List<Validator> validators;

    public AdminCompoundValidator(List<Validator> validators) {
        super();
        logger.debug(validators.size() + " validators are into compound validator");
        this.validators = validators;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        logger.debug("Class for validation: " + aClass.getSimpleName());
        for(Validator v : validators){
            if(v.supports(aClass)){
                return true;
            }
            logger.debug("Validator " + v.getClass().getSimpleName() + " doesn't support this form");
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
