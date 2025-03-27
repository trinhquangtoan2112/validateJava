package edu.java.helloworld.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneNumber, String> {
private String value;
    @Override
    public void initialize(PhoneNumber phoneNumberNo) {
       this.value=phoneNumberNo.value();
    }

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext cxt) {
        if(phoneNo ==null){
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("SDT khong duoc bo trong").addConstraintViolation();
            return false;
        }
        System.out.println(value+" 4242412412");
      return true;
    }

    
   
    
}
