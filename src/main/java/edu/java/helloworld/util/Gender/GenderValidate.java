package edu.java.helloworld.util.Gender;


import java.util.Arrays;

import edu.java.helloworld.model.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidate implements ConstraintValidator<GenderAnotation, Gender> {
    private Gender[] gender;
      @Override
    public void initialize(GenderAnotation anotation) {
        this.gender =anotation.anyOf();
    }



    @Override
    public boolean isValid( Gender value, ConstraintValidatorContext arg1) {
       
        return value == null || Arrays.asList(gender).contains(value);
       
    }
    
}
