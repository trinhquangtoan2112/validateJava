package edu.java.helloworld.util.Gender;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import edu.java.helloworld.model.Gender;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = GenderValidate.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderAnotation {
  Gender[] anyOf();
  String message() default "Loi xay ra";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
   
}
