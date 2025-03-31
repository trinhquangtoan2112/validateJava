package edu.java.helloworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.java.helloworld.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    
}
