package main.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    @Override
    public void initialize(ValidName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
}
