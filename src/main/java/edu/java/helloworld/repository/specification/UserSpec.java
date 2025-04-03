package edu.java.helloworld.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import edu.java.helloworld.model.Gender;
import edu.java.helloworld.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserSpec {
    
    public static Specification<User>  hasFirstName(String firstName){
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
              
           return criteriaBuilder.like(root.get("firstName"),String.format("%%%s%%", firstName));
            }
        
        };
    }
    public static Specification<User>  notEqualsGender(Gender gender){
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
              
           return criteriaBuilder.notEqual(root.get("gender"),Gender.MALE);
            }
        
        };
    }
}
