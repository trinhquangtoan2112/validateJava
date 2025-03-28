package edu.java.helloworld.util.listEnum;

import java.util.List;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, CharSequence> {
    private List acceptedValues;

    @Override
    public void initialize(EnumValue enumValue) {
        acceptedValues = Stream.of(enumValue.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext arg1) {
        if (value == null) {
            return true;
        }
        return true;
        // return acceptedValues.contains(value.toString().toUpperCase());
    }
    
}
