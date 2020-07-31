package main.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Locale;

public class CountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {

    @Override
    public void initialize(ValidCountryCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(Locale.getISOCountries()).anyMatch(p -> p.equalsIgnoreCase(s));
    }
}
