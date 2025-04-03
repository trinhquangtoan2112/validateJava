package edu.java.helloworld.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber phoneNumberNo) {
     
    }

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext cxt) {
        if(phoneNo ==null){
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("SDT khong duoc bo trong").addConstraintViolation();
            return false;
        }
     
      return true;
    }

    
   
    
}
